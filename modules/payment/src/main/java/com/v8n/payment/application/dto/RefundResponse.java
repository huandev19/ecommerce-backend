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
public class RefundResponse {
    private UUID id;
    private UUID collectionId;
    private int amount;
    private String currencyCode;
    private com.v8n.payment.domain.entity.Refund.RefundStatus status;
    private String reason;
    private String providerRefundId;
    private LocalDateTime processedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}