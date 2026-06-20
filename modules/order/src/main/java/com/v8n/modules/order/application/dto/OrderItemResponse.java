package com.v8n.modules.order.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemResponse {
    private UUID id;
    private UUID variantId;
    private String title;
    private String sku;
    private int quantity;
    private int unitPrice;
    private int subtotal;
    private Map<String, Object> metadata;
}