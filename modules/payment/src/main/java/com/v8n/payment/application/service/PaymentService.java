package com.v8n.payment.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.order.domain.entity.Order;
import com.v8n.modules.order.domain.repository.OrderRepository;
import com.v8n.payment.application.dto.PaymentCollectionResponse;
import com.v8n.payment.application.dto.PaymentSessionRequest;
import com.v8n.payment.application.dto.PaymentSessionResponse;
import com.v8n.payment.application.dto.RefundRequest;
import com.v8n.payment.application.dto.RefundResponse;
import com.v8n.payment.domain.entity.Payment;
import com.v8n.payment.domain.entity.Payment.PaymentStatus;
import com.v8n.payment.domain.entity.PaymentCollection;
import com.v8n.payment.domain.entity.PaymentCollection.PaymentCollectionStatus;
import com.v8n.payment.domain.entity.PaymentSession;
import com.v8n.payment.domain.entity.PaymentSession.PaymentSessionStatus;
import com.v8n.payment.domain.entity.Refund;
import com.v8n.payment.domain.entity.Refund.RefundStatus;
import com.v8n.payment.domain.repository.PaymentCollectionRepository;
import com.v8n.payment.domain.repository.PaymentRepository;
import com.v8n.payment.domain.repository.PaymentSessionRepository;
import com.v8n.payment.domain.repository.RefundRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentCollectionRepository paymentCollectionRepository;
    private final PaymentSessionRepository paymentSessionRepository;
    private final PaymentRepository paymentRepository;
    private final RefundRepository refundRepository;
    private final OrderRepository orderRepository;

    // ===== Payment Collection Methods =====

    @Transactional(readOnly = true)
    public PaymentCollectionResponse getCollectionById(UUID collectionId) {
        PaymentCollection collection = findCollectionById(collectionId);
        return toCollectionResponse(collection);
    }

    @Transactional(readOnly = true)
    public PaymentCollectionResponse getCollectionByOrderId(UUID orderId) {
        PaymentCollection collection = paymentCollectionRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_FAILED, "Payment collection not found for order"));
        return toCollectionResponse(collection);
    }

    @Transactional
    public PaymentCollectionResponse createCollectionForOrder(UUID orderId) {
        Order order = orderRepository.findByIdNotDeleted(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        paymentCollectionRepository.findByOrderIdAndStatus(orderId, PaymentCollectionStatus.PENDING)
                .ifPresent(existing -> {
                    throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Pending payment collection already exists for this order");
                });

        PaymentCollection collection = new PaymentCollection();
        collection.setOrder(order);
        collection.setCurrencyCode(order.getCurrencyCode());
        collection.setAmount(order.getTotal());
        collection.setStatus(PaymentCollectionStatus.PENDING);

        collection = paymentCollectionRepository.save(collection);
        log.info("Created payment collection {} for order {}", collection.getId(), orderId);
        return toCollectionResponse(collection);
    }

    // ===== Payment Session Methods =====

    @Transactional(readOnly = true)
    public PaymentSessionResponse getSessionById(UUID sessionId) {
        PaymentSession session = findSessionById(sessionId);
        return toSessionResponse(session);
    }

    @Transactional
    public PaymentSessionResponse createSession(UUID collectionId, PaymentSessionRequest request) {
        PaymentCollection collection = findCollectionById(collectionId);

        PaymentSession session = new PaymentSession();
        session.setPaymentCollection(collection);
        session.setProvider(request.getProvider());
        session.setAmount(request.getAmount() > 0 ? request.getAmount() : collection.getAmount());
        session.setCurrencyCode(collection.getCurrencyCode());
        session.setStatus(PaymentSessionStatus.PENDING);

        LocalDateTime expiresAt = request.getExpiresAtMinutes() > 0
                ? LocalDateTime.now().plusMinutes(request.getExpiresAtMinutes())
                : LocalDateTime.now().plusMinutes(30);
        session.setExpiresAt(expiresAt);

        session = paymentSessionRepository.save(session);
        collection.addSession(session);
        paymentCollectionRepository.save(collection);

        log.info("Created payment session {} for collection {}", session.getId(), collectionId);
        return toSessionResponse(session);
    }

    @Transactional
    public PaymentSessionResponse confirmSession(UUID sessionId, String providerSessionId) {
        PaymentSession session = findSessionById(sessionId);

        if (session.isExpired()) {
            session.setStatus(PaymentSessionStatus.EXPIRED);
            paymentSessionRepository.save(session);
            throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Payment session has expired");
        }

        session.setProviderSessionId(providerSessionId);
        session.setStatus(PaymentSessionStatus.AUTHORIZED);
        session.setConfirmedAt(LocalDateTime.now());

        PaymentCollection collection = session.getPaymentCollection();
        collection.setAuthorizedAmount(collection.getAuthorizedAmount() + session.getAmount());
        if (collection.getAuthorizedAmount() >= collection.getAmount()) {
            collection.setStatus(PaymentCollectionStatus.AUTHORIZED);
        }

        paymentSessionRepository.save(session);
        paymentCollectionRepository.save(collection);

        log.info("Confirmed payment session {} with provider session {}", sessionId, providerSessionId);
        return toSessionResponse(session);
    }

    @Transactional
    public PaymentSessionResponse cancelSession(UUID sessionId) {
        PaymentSession session = findSessionById(sessionId);

        if (session.getStatus() != PaymentSessionStatus.PENDING && session.getStatus() != PaymentSessionStatus.REQUIRES_ACTION) {
            throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Cannot cancel session in status: " + session.getStatus());
        }

        session.setStatus(PaymentSessionStatus.CANCELLED);
        session.setCanceledAt(LocalDateTime.now());
        paymentSessionRepository.save(session);

        log.info("Cancelled payment session {}", sessionId);
        return toSessionResponse(session);
    }

    // ===== Payment Capture Methods =====

    @Transactional
    public PaymentSessionResponse capturePayment(UUID sessionId) {
        PaymentSession session = findSessionById(sessionId);

        if (session.getStatus() != PaymentSessionStatus.AUTHORIZED) {
            throw new BusinessException(ErrorCode.PAYMENT_NOT_AUTHORIZED, "Session must be authorized before capture");
        }

        Payment payment = new Payment();
        payment.setPaymentCollection(session.getPaymentCollection());
        payment.setPaymentSession(session);
        payment.setAmount(session.getAmount());
        payment.setCurrencyCode(session.getCurrencyCode());
        payment.setStatus(PaymentStatus.CAPTURED);
        payment.setCapturedAt(LocalDateTime.now());
        paymentRepository.save(payment);

        session.setStatus(PaymentSessionStatus.CAPTURED);
        paymentSessionRepository.save(session);

        PaymentCollection collection = session.getPaymentCollection();
        collection.setCapturedAmount(collection.getCapturedAmount() + payment.getAmount());
        if (collection.getCapturedAmount() >= collection.getAmount()) {
            collection.setStatus(PaymentCollectionStatus.CAPTURED);
        }
        paymentCollectionRepository.save(collection);

        log.info("Captured payment {} from session {}", payment.getId(), sessionId);
        return toSessionResponse(session);
    }

    // ===== Refund Methods =====

    @Transactional(readOnly = true)
    public RefundResponse getRefundById(UUID refundId) {
        Refund refund = findRefundById(refundId);
        return toRefundResponse(refund);
    }

    @Transactional
    public RefundResponse createRefund(UUID collectionId, RefundRequest request) {
        PaymentCollection collection = findCollectionById(collectionId);

        if (collection.getCapturedAmount() < request.getAmount()) {
            throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Refund amount exceeds captured amount");
        }

        Refund refund = new Refund();
        refund.setPaymentCollection(collection);
        refund.setAmount(request.getAmount());
        refund.setCurrencyCode(collection.getCurrencyCode());
        refund.setReason(request.getReason());
        refund.setStatus(RefundStatus.PENDING);

        collection.addRefund(refund);
        paymentCollectionRepository.save(collection);
        refund = refundRepository.save(refund);

        log.info("Created refund {} for collection {}", refund.getId(), collectionId);
        return toRefundResponse(refund);
    }

    @Transactional
    public RefundResponse processRefund(UUID refundId, String providerRefundId) {
        Refund refund = findRefundById(refundId);

        if (refund.getStatus() != RefundStatus.PENDING) {
            throw new BusinessException(ErrorCode.PAYMENT_FAILED, "Refund must be in PENDING status to process");
        }

        refund.setStatus(RefundStatus.PROCESSED);
        refund.setProviderRefundId(providerRefundId);
        refund.setProcessedAt(LocalDateTime.now());

        PaymentCollection collection = refund.getPaymentCollection();
        collection.setRefundedAmount(collection.getRefundedAmount() + refund.getAmount());
        if (collection.getRefundedAmount() >= collection.getCapturedAmount()) {
            collection.setStatus(PaymentCollectionStatus.REFUNDED);
        } else if (collection.getRefundedAmount() > 0) {
            collection.setStatus(PaymentCollectionStatus.PARTIALLY_REFUNDED);
        }
        paymentCollectionRepository.save(collection);
        refundRepository.save(refund);

        log.info("Processed refund {} with provider refund {}", refundId, providerRefundId);
        return toRefundResponse(refund);
    }

    // ===== Private Helper Methods =====

    private PaymentCollection findCollectionById(UUID id) {
        return paymentCollectionRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_FAILED, "Payment collection not found"));
    }

    private PaymentSession findSessionById(UUID id) {
        return paymentSessionRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_SESSION_NOT_FOUND));
    }

    private Refund findRefundById(UUID id) {
        return refundRepository.findByIdNotDeleted(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PAYMENT_FAILED, "Refund not found"));
    }

    private PaymentCollectionResponse toCollectionResponse(PaymentCollection collection) {
        return PaymentCollectionResponse.builder()
                .id(collection.getId())
                .orderId(collection.getOrder().getId())
                .currencyCode(collection.getCurrencyCode())
                .status(collection.getStatus())
                .amount(collection.getAmount())
                .authorizedAmount(collection.getAuthorizedAmount())
                .capturedAmount(collection.getCapturedAmount())
                .refundedAmount(collection.getRefundedAmount())
                .createdAt(collection.getCreatedAt())
                .updatedAt(collection.getUpdatedAt())
                .build();
    }

    private PaymentSessionResponse toSessionResponse(PaymentSession session) {
        return PaymentSessionResponse.builder()
                .id(session.getId())
                .collectionId(session.getPaymentCollection().getId())
                .provider(session.getProvider())
                .providerSessionId(session.getProviderSessionId())
                .status(session.getStatus())
                .amount(session.getAmount())
                .currencyCode(session.getCurrencyCode())
                .expiresAt(session.getExpiresAt())
                .confirmedAt(session.getConfirmedAt())
                .createdAt(session.getCreatedAt())
                .updatedAt(session.getUpdatedAt())
                .build();
    }

    private RefundResponse toRefundResponse(Refund refund) {
        return RefundResponse.builder()
                .id(refund.getId())
                .collectionId(refund.getPaymentCollection().getId())
                .amount(refund.getAmount())
                .currencyCode(refund.getCurrencyCode())
                .status(refund.getStatus())
                .reason(refund.getReason())
                .providerRefundId(refund.getProviderRefundId())
                .processedAt(refund.getProcessedAt())
                .createdAt(refund.getCreatedAt())
                .updatedAt(refund.getUpdatedAt())
                .build();
    }
}