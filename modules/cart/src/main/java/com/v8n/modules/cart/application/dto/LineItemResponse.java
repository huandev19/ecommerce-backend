package com.v8n.modules.cart.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LineItemResponse {

    private UUID id;
    private UUID cartId;
    private UUID variantId;
    private String variantSku;
    private String title;
    private int quantity;
    private int unitPrice; // In cents
    private long subtotal; // In cents
    private String thumbnail;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}