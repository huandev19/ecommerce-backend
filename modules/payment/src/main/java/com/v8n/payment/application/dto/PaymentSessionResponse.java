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
public class PaymentSessionResponse {
    private UUID id;
    private UUID collectionId;
    private String provider;
    private String providerSessionId;
    private com.v8n.payment.domain.entity.PaymentSession.PaymentSessionStatus status;
    private int amount;
    private String currencyCode;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}