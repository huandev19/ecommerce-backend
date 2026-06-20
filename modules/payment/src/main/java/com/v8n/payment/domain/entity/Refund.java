package com.v8n.payment.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "refunds")
@Getter
@Setter
@NoArgsConstructor
public class Refund extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_collection_id", nullable = false)
    @NotNull
    private PaymentCollection paymentCollection;

    @Column(name = "amount", nullable = false)
    private int amount = 0;

    @Column(name = "currency_code", nullable = false, length = 3)
    @NotNull
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private RefundStatus status = RefundStatus.PENDING;

    @Column(name = "reason", length = 255)
    private String reason;

    @Column(name = "provider_refund_id", length = 255)
    private String providerRefundId;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "failed_at")
    private LocalDateTime failedAt;

    @Column(name = "failure_message", length = 500)
    private String failureMessage;

    public enum RefundStatus {
        PENDING,
        PROCESSED,
        FAILED,
        CANCELLED
    }

    public boolean isPending() {
        return status == RefundStatus.PENDING;
    }

    public boolean isProcessed() {
        return status == RefundStatus.PROCESSED;
    }
}