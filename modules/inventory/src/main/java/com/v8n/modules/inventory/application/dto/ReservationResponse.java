package com.v8n.modules.inventory.application.dto;

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
public class ReservationResponse {
    private UUID id;
    private UUID inventoryItemId;
    private UUID lineItemId;
    private int quantity;
    private String status;
    private LocalDateTime expiresAt;
    private boolean isActive;
    private LocalDateTime createdAt;
}