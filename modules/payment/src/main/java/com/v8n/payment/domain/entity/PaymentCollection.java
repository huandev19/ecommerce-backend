package com.v8n.payment.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payment_collections")
@Getter
@Setter
@NoArgsConstructor
public class PaymentCollection extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    private com.v8n.modules.order.domain.entity.Order order;

    @Column(name = "currency_code", nullable = false, length = 3)
    @NotNull
    private String currencyCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private PaymentCollectionStatus status = PaymentCollectionStatus.PENDING;

    @Column(name = "amount", nullable = false)
    private int amount = 0;

    @Column(name = "authorized_amount")
    private int authorizedAmount = 0;

    @Column(name = "captured_amount")
    private int capturedAmount = 0;

    @Column(name = "refunded_amount")
    private int refundedAmount = 0;

    @OneToMany(mappedBy = "paymentCollection", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<PaymentSession> sessions = new ArrayList<>();

    @OneToMany(mappedBy = "paymentCollection", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<Refund> refunds = new ArrayList<>();

    public enum PaymentCollectionStatus {
        PENDING,
        AUTHORIZED,
        PARTIALLY_CAPTURED,
        CAPTURED,
        PARTIALLY_REFUNDED,
        REFUNDED,
        CANCELLED
    }

    public void addSession(PaymentSession session) {
        sessions.add(session);
        session.setPaymentCollection(this);
    }

    public void addRefund(Refund refund) {
        refunds.add(refund);
        refund.setPaymentCollection(this);
    }

    public int getAuthorizedAmount() {
        return authorizedAmount;
    }

    public int getCapturedAmount() {
        return capturedAmount;
    }

    public int getRefundedAmount() {
        return refundedAmount;
    }
}