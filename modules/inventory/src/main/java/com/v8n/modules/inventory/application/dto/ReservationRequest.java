package com.v8n.modules.inventory.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationRequest {
    private UUID inventoryItemId;
    private UUID lineItemId;
    private int quantity;
}