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
@Table(name = "payment_sessions")
@Getter
@Setter
@NoArgsConstructor
public class PaymentSession extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_collection_id", nullable = false)
    @NotNull
    private PaymentCollection paymentCollection;

    @Column(name = "provider", nullable = false, length = 50)
    @NotNull
    private String provider;

    @Column(name = "provider_session_id", length = 255)
    private String providerSessionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private PaymentSessionStatus status = PaymentSessionStatus.PENDING;

    @Column(name = "amount", nullable = false)
    private int amount = 0;

    @Column(name = "currency_code", nullable = false, length = 3)
    @NotNull
    private String currencyCode;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "confirmed_at")
    private LocalDateTime confirmedAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "error_message", length = 500)
    private String errorMessage;

    public enum PaymentSessionStatus {
        PENDING,
        REQUIRES_ACTION,
        AUTHORIZED,
        CAPTURED,
        CANCELLED,
        FAILED,
        EXPIRED
    }

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isPending() {
        return status == PaymentSessionStatus.PENDING || status == PaymentSessionStatus.REQUIRES_ACTION;
    }
}