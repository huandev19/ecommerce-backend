package com.v8n.fulfillment.application.dto;

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
public class FulfillmentResponse {
    private UUID id;
    private UUID orderId;
    private Long displayId;
    private com.v8n.fulfillment.domain.entity.Fulfillment.FulfillmentStatus status;
    private String trackingNumber;
    private String carrier;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private LocalDateTime canceledAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}