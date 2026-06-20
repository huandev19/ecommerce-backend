package com.v8n.notification.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
public class Notification extends BaseEntity {

    @Column(name = "recipient_id", nullable = false)
    private java.util.UUID recipientId;

    @Column(name = "recipient_email", length = 255)
    private String recipientEmail;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(name = "channel", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Channel channel;

    @Column(name = "subject", length = 255)
    private String subject;

    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private NotificationStatus status = NotificationStatus.PENDING;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    @Column(name = "metadata", columnDefinition = "jsonb")
    @org.hibernate.annotations.JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    private java.util.Map<String, Object> metadata;

    public enum NotificationType {
        ORDER_CONFIRMATION,
        ORDER_SHIPPED,
        ORDER_DELIVERED,
        ORDER_CANCELLED,
        PAYMENT_RECEIVED,
        PAYMENT_FAILED,
        REFUND_PROCESSED,
        CUSTOMER_WELCOME,
        PASSWORD_RESET,
        ACCOUNT_VERIFICATION
    }

    public enum Channel {
        EMAIL,
        SMS,
        PUSH,
        IN_APP
    }

    public enum NotificationStatus {
        PENDING,
        SENT,
        READ,
        FAILED,
        CANCELLED
    }

    public boolean isPending() {
        return status == NotificationStatus.PENDING;
    }

    public boolean isSent() {
        return status == NotificationStatus.SENT;
    }

    public boolean isRead() {
        return status == NotificationStatus.READ;
    }

    public boolean isFailed() {
        return status == NotificationStatus.FAILED;
    }

    public void markAsSent() {
        this.status = NotificationStatus.SENT;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsRead() {
        this.status = NotificationStatus.READ;
        this.readAt = LocalDateTime.now();
    }

    public void markAsFailed(String errorMessage) {
        this.status = NotificationStatus.FAILED;
        this.failedAt = LocalDateTime.now();
        this.errorMessage = errorMessage;
    }
}