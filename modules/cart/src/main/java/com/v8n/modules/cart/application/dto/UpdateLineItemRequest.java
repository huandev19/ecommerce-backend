package com.v8n.modules.cart.application.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateLineItemRequest {

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}