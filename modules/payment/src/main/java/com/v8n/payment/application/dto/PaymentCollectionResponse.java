package com.v8n.payment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCollectionResponse {
    private UUID id;
    private UUID orderId;
    private String currencyCode;
    private com.v8n.payment.domain.entity.PaymentCollection.PaymentCollectionStatus status;
    private int amount;
    private int authorizedAmount;
    private int capturedAmount;
    private int refundedAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}