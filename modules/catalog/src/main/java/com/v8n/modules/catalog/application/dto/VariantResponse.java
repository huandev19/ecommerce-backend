package com.v8n.modules.catalog.application.dto;

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
public class VariantResponse {
    private UUID id;
    private UUID productId;
    private String title;
    private String sku;
    private String barcode;
    private String ean;
    private String upc;
    private int inventoryQuantity;
    private boolean allowBackorder;
    private boolean manageInventory;
    private Integer weight;
    private Integer height;
    private Integer width;
    private Integer length;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}