package com.v8n.notification.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.notification.application.dto.NotificationRequest;
import com.v8n.notification.application.dto.NotificationResponse;
import com.v8n.notification.domain.entity.Notification;
import com.v8n.notification.domain.entity.Notification.Channel;
import com.v8n.notification.domain.entity.Notification.NotificationStatus;
import com.v8n.notification.domain.entity.Notification.NotificationType;
import com.v8n.notification.domain.repository.NotificationRepository;
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
public class NotificationService {

    private final NotificationRepository notificationRepository;

    // ===== Notification CRUD Methods =====

    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(UUID notificationId) {
        Notification notification = findNotificationById(notificationId);
        return toNotificationResponse(notification);
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getNotificationsByRecipientId(UUID recipientId) {
        return notificationRepository.findByRecipientId(recipientId).stream()
                .map(this::toNotificationResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<NotificationResponse> getUnreadNotifications(UUID recipientId) {
        return notificationRepository.findByRecipientIdAndStatus(recipientId, NotificationStatus.SENT).stream()
                .map(this::toNotificationResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setRecipientId(request.getRecipientId());
        notification.setRecipientEmail(request.getRecipientEmail());
        notification.setType(request.getType());
        notification.setChannel(request.getChannel() != null ? request.getChannel() : Channel.EMAIL);
        notification.setSubject(request.getSubject());
        notification.setContent(request.getContent());
        notification.setStatus(NotificationStatus.PENDING);

        notification = notificationRepository.save(notification);
        log.info("Created notification: {} for recipient: {}", notification.getId(), request.getRecipientId());
        return toNotificationResponse(notification);
    }

    @Transactional
    public NotificationResponse sendNotification(UUID notificationId) {
        Notification notification = findNotificationById(notificationId);

        if (!notification.isPending()) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "Notification is not in PENDING status");
        }

        try {
            sendViaChannel(notification);
            notification.markAsSent();
            log.info("Sent notification: {} via {}", notificationId, notification.getChannel());
        } catch (Exception e) {
            notification.markAsFailed(e.getMessage());
            log.error("Failed to send notification: {}", notificationId, e);
        }

        notificationRepository.save(notification);
        return toNotificationResponse(notification);
    }

    @Transactional
    public NotificationResponse markAsRead(UUID notificationId) {
        Notification notification = findNotificationById(notificationId);

        if (notification.getStatus() != NotificationStatus.SENT) {
            throw new BusinessException(ErrorCode.INTERNAL_ERROR, "Notification must be SENT before marking as READ");
        }

        notification.markAsRead();
        notificationRepository.save(notification);
        log.info("Marked notification {} as read", notificationId);
        return toNotificationResponse(notification);
    }

    @Transactional
    public void markAllAsRead(UUID recipientId) {
        List<Notification> unread = notificationRepository.findByRecipientIdAndStatus(recipientId, NotificationStatus.SENT);
        for (Notification notification : unread) {
            notification.markAsRead();
            notificationRepository.save(notification);
        }
        log.info("Marked {} notifications as read for recipient {}", unread.size(), recipientId);
    }

    @Transactional
    public void deleteNotification(UUID notificationId) {
        Notification notification = findNotificationById(notificationId);
        notificationRepository.delete(notification);
        log.info("Deleted notification: {}", notificationId);
    }

    // ===== Order-related Notifications =====

    @Transactional
    public NotificationResponse sendOrderConfirmation(UUID recipientId, String email, String orderDisplayId) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setRecipientEmail(email);
        notification.setType(NotificationType.ORDER_CONFIRMATION);
        notification.setChannel(Channel.EMAIL);
        notification.setSubject("Order Confirmation - #" + orderDisplayId);
        notification.setContent("Thank you for your order #" + orderDisplayId + "! We have received your order and will process it shortly.");
        notification.setStatus(NotificationStatus.PENDING);

        notification = notificationRepository.save(notification);
        return sendNotification(notification.getId());
    }

    @Transactional
    public NotificationResponse sendOrderShipped(UUID recipientId, String email, String orderDisplayId, String trackingNumber) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setRecipientEmail(email);
        notification.setType(NotificationType.ORDER_SHIPPED);
        notification.setChannel(Channel.EMAIL);
        notification.setSubject("Your Order #" + orderDisplayId + " Has Been Shipped!");
        notification.setContent("Your order #" + orderDisplayId + " has been shipped. Tracking number: " + trackingNumber);
        notification.setStatus(NotificationStatus.PENDING);

        notification = notificationRepository.save(notification);
        return sendNotification(notification.getId());
    }

    @Transactional
    public NotificationResponse sendOrderDelivered(UUID recipientId, String email, String orderDisplayId) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setRecipientEmail(email);
        notification.setType(NotificationType.ORDER_DELIVERED);
        notification.setChannel(Channel.EMAIL);
        notification.setSubject("Your Order #" + orderDisplayId + " Has Been Delivered");
        notification.setContent("Your order #" + orderDisplayId + " has been delivered. Thank you for shopping with us!");
        notification.setStatus(NotificationStatus.PENDING);

        notification = notificationRepository.save(notification);
        return sendNotification(notification.getId());
    }

    @Transactional
    public NotificationResponse sendPaymentReceived(UUID recipientId, String email, String orderDisplayId, int amount) {
        Notification notification = new Notification();
        notification.setRecipientId(recipientId);
        notification.setRecipientEmail(email);
        notification.setType(NotificationType.PAYMENT_RECEIVED);
        notification.setChannel(Channel.EMAIL);
        notification.setSubject("Payment Received for Order #" + orderDisplayId);
        notification.setContent("We have received your payment of " + (amount / 100.0) + " for order #" + orderDisplayId + ".");
        notification.setStatus(NotificationStatus.PENDING);

        notification = notificationRepository.save(notification);
        return sendNotification(notification.getId());
    }

    // ===== Private Helper Methods =====

    private Notification findNotificationById(UUID notificationId) {
        return notificationRepository.findByIdNotDeleted(notificationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_ERROR, "Notification not found"));
    }

    private void sendViaChannel(Notification notification) {
        switch (notification.getChannel()) {
            case EMAIL:
                sendEmail(notification);
                break;
            case SMS:
                sendSms(notification);
                break;
            case PUSH:
                sendPush(notification);
                break;
            case IN_APP:
                break;
        }
    }

    private void sendEmail(Notification notification) {
        log.info("Sending EMAIL to {}: {}", notification.getRecipientEmail(), notification.getSubject());
    }

    private void sendSms(Notification notification) {
        log.info("Sending SMS to recipient {}: {}", notification.getRecipientId(), notification.getContent());
    }

    private void sendPush(Notification notification) {
        log.info("Sending PUSH notification to recipient {}: {}", notification.getRecipientId(), notification.getSubject());
    }

    private NotificationResponse toNotificationResponse(Notification notification) {
        return NotificationResponse.builder()
                .id(notification.getId())
                .recipientId(notification.getRecipientId())
                .recipientEmail(notification.getRecipientEmail())
                .type(notification.getType())
                .channel(notification.getChannel())
                .subject(notification.getSubject())
                .status(notification.getStatus())
                .sentAt(notification.getSentAt())
                .readAt(notification.getReadAt())
                .createdAt(notification.getCreatedAt())
                .updatedAt(notification.getUpdatedAt())
                .build();
    }
}