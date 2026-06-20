package com.v8n.modules.cart.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AddLineItemRequest {

    @NotNull(message = "Variant ID is required")
    private UUID variantId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;

    @NotNull(message = "Unit price is required")
    @Min(value = 0, message = "Unit price must be non-negative")
    private int unitPrice; // In cents

    @NotNull(message = "Title is required")
    private String title;

    private String thumbnail;
}