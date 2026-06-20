package com.v8n.modules.catalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VariantRequest {
    private String title;
    private String sku;
    private String barcode;
    private String ean;
    private String upc;
    private int inventoryQuantity;
    private boolean allowBackorder;
    private boolean manageInventory = true;
    private Integer weight;
    private Integer height;
    private Integer width;
    private Integer length;
}