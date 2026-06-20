package com.v8n.fulfillment.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.order.domain.entity.Order;
import com.v8n.modules.order.domain.entity.OrderItem;
import com.v8n.modules.order.domain.repository.OrderRepository;
import com.v8n.fulfillment.application.dto.FulfillmentRequest;
import com.v8n.fulfillment.application.dto.FulfillmentResponse;
import com.v8n.fulfillment.domain.entity.Fulfillment;
import com.v8n.fulfillment.domain.entity.Fulfillment.FulfillmentStatus;
import com.v8n.fulfillment.domain.entity.FulfillmentItem;
import com.v8n.fulfillment.domain.repository.FulfillmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FulfillmentService {

    private final FulfillmentRepository fulfillmentRepository;
    private final OrderRepository orderRepository;

    // ===== Fulfillment Methods =====

    @Transactional(readOnly = true)
    public FulfillmentResponse getFulfillmentById(UUID fulfillmentId) {
        Fulfillment fulfillment = findFulfillmentById(fulfillmentId);
        return toFulfillmentResponse(fulfillment);
    }

    @Transactional(readOnly = true)
    public List<FulfillmentResponse> getFulfillmentByOrderId(UUID orderId) {
        return fulfillmentRepository.findByOrderId(orderId).stream()
                .map(this::toFulfillmentResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public FulfillmentResponse createFulfillment(UUID orderId, FulfillmentRequest request) {
        Order order = orderRepository.findByIdNotDeleted(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        Fulfillment fulfillment = new Fulfillment();
        fulfillment.setOrder(order);
        fulfillment.setStatus(FulfillmentStatus.NOT_FULFILLED);

        if (request.getTrackingNumber() != null) {
            fulfillment.setTrackingNumber(request.getTrackingNumber());
        }
        if (request.getCarrier() != null) {
            fulfillment.setCarrier(request.getCarrier());
        }

        for (OrderItem item : order.getItems()) {
            FulfillmentItem fulfillmentItem = new FulfillmentItem();
            fulfillmentItem.setFulfillment(fulfillment);
            fulfillmentItem.setVariant(item.getVariant());
            fulfillmentItem.setTitle(item.getTitle());
            fulfillmentItem.setSku(item.getSku());
            fulfillmentItem.setQuantity(item.getQuantity());
            fulfillmentItem.setFulfilledQuantity(0);
            fulfillmentItem.setReturnedQuantity(0);
            fulfillment.addItem(fulfillmentItem);
        }

        fulfillment = fulfillmentRepository.save(fulfillment);
        log.info("Created fulfillment {} for order {}", fulfillment.getId(), orderId);
        return toFulfillmentResponse(fulfillment);
    }

    @Transactional
    public FulfillmentResponse shipFulfillment(UUID fulfillmentId, FulfillmentRequest request) {
        Fulfillment fulfillment = findFulfillmentById(fulfillmentId);

        if (fulfillment.getStatus() != FulfillmentStatus.NOT_FULFILLED &&
            fulfillment.getStatus() != FulfillmentStatus.PARTIALLY_FULFILLED) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cannot ship fulfillment in status: " + fulfillment.getStatus());
        }

        if (request.getTrackingNumber() != null) {
            fulfillment.setTrackingNumber(request.getTrackingNumber());
        }
        if (request.getCarrier() != null) {
            fulfillment.setCarrier(request.getCarrier());
        }

        fulfillment.setShippedAt(LocalDateTime.now());

        int totalItems = fulfillment.getItems().stream().mapToInt(FulfillmentItem::getQuantity).sum();
        int fulfilledItems = fulfillment.getItems().stream().mapToInt(FulfillmentItem::getFulfilledQuantity).sum();

        if (fulfilledItems >= totalItems) {
            fulfillment.setStatus(FulfillmentStatus.FULFILLED);
        } else if (fulfilledItems > 0) {
            fulfillment.setStatus(FulfillmentStatus.PARTIALLY_FULFILLED);
        }

        fulfillment = fulfillmentRepository.save(fulfillment);
        log.info("Shipped fulfillment {} with tracking {}", fulfillmentId, fulfillment.getTrackingNumber());
        return toFulfillmentResponse(fulfillment);
    }

    @Transactional
    public FulfillmentResponse deliverFulfillment(UUID fulfillmentId) {
        Fulfillment fulfillment = findFulfillmentById(fulfillmentId);

        if (fulfillment.getStatus() != FulfillmentStatus.FULFILLED) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Fulfillment must be shipped before marking as delivered");
        }

        fulfillment.setDeliveredAt(LocalDateTime.now());
        fulfillment = fulfillmentRepository.save(fulfillment);

        log.info("Delivered fulfillment {}", fulfillmentId);
        return toFulfillmentResponse(fulfillment);
    }

    @Transactional
    public FulfillmentResponse cancelFulfillment(UUID fulfillmentId) {
        Fulfillment fulfillment = findFulfillmentById(fulfillmentId);

        if (fulfillment.getStatus() == FulfillmentStatus.RETURNED ||
            fulfillment.getStatus() == FulfillmentStatus.CANCELLED) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cannot cancel fulfillment in status: " + fulfillment.getStatus());
        }

        fulfillment.setCanceledAt(LocalDateTime.now());
        fulfillment.setStatus(FulfillmentStatus.CANCELLED);
        fulfillment = fulfillmentRepository.save(fulfillment);

        log.info("Cancelled fulfillment {}", fulfillmentId);
        return toFulfillmentResponse(fulfillment);
    }

    @Transactional
    public FulfillmentResponse returnFulfillment(UUID fulfillmentId, int returnQuantity) {
        Fulfillment fulfillment = findFulfillmentById(fulfillmentId);

        if (fulfillment.getDeliveredAt() == null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Fulfillment must be delivered before processing return");
        }

        for (FulfillmentItem item : fulfillment.getItems()) {
            item.setReturnedQuantity(item.getReturnedQuantity() + returnQuantity);
        }

        fulfillment.setStatus(FulfillmentStatus.PARTIALLY_RETURNED);
        fulfillment = fulfillmentRepository.save(fulfillment);

        log.info("Processed return for fulfillment {} with quantity {}", fulfillmentId, returnQuantity);
        return toFulfillmentResponse(fulfillment);
    }

    // ===== Private Helper Methods =====

    private Fulfillment findFulfillmentById(UUID fulfillmentId) {
        return fulfillmentRepository.findByIdNotDeleted(fulfillmentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST, "Fulfillment not found"));
    }

    private FulfillmentResponse toFulfillmentResponse(Fulfillment fulfillment) {
        return FulfillmentResponse.builder()
                .id(fulfillment.getId())
                .orderId(fulfillment.getOrder().getId())
                .displayId(fulfillment.getDisplayId())
                .status(fulfillment.getStatus())
                .trackingNumber(fulfillment.getTrackingNumber())
                .carrier(fulfillment.getCarrier())
                .shippedAt(fulfillment.getShippedAt())
                .deliveredAt(fulfillment.getDeliveredAt())
                .canceledAt(fulfillment.getCanceledAt())
                .createdAt(fulfillment.getCreatedAt())
                .updatedAt(fulfillment.getUpdatedAt())
                .build();
    }
}