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
public class InventoryItemResponse {
    private UUID id;
    private UUID variantId;
    private String sku;
    private String title;
    private int quantity;
    private int reservedQuantity;
    private int availableQuantity;
    private int incomingQuantity;
    private String location;
    private boolean requiresShipping;
    private boolean overselling;
    private Integer restockThreshold;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}