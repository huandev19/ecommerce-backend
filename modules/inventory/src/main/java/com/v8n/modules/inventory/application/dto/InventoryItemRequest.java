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
public class InventoryItemRequest {
    private UUID variantId;
    private String sku;
    private String title;
    private String location;
    private boolean requiresShipping = true;
    private boolean overselling;
    private Integer restockThreshold;
}