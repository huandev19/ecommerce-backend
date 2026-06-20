package com.v8n.modules.order.domain.entity;

public enum PaymentStatus {
    NOT_PAID,
    AWAITING,
    AUTHORIZED,
    PARTIALLY_CAPTURED,
    CAPTURED,
    PARTIALLY_REFUNDED,
    REFUNDED
}
