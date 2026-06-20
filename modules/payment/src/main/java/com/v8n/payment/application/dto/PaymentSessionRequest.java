package com.v8n.payment.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentSessionRequest {
    private String provider;
    private int amount;
    private int expiresAtMinutes = 30;
}