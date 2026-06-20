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
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_collection_id", nullable = false)
    @NotNull
    private PaymentCollection paymentCollection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_session_id")
    private PaymentSession paymentSession;

    @Column(name = "amount", nullable = false)
    private int amount = 0;

    @Column(name = "currency_code", nullable = false, length = 3)
    @NotNull
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private PaymentStatus status = PaymentStatus.PENDING;

    @Column(name = "captured_at")
    private LocalDateTime capturedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "provider_transaction_id", length = 255)
    private String providerTransactionId;

    public enum PaymentStatus {
        PENDING,
        AUTHORIZED,
        CAPTURED,
        PARTIALLY_REFUNDED,
        REFUNDED,
        CANCELLED,
        FAILED
    }

    public boolean isCaptured() {
        return status == PaymentStatus.CAPTURED;
    }

    public boolean isRefundable() {
        return status == PaymentStatus.CAPTURED || status == PaymentStatus.PARTIALLY_REFUNDED;
    }
}