package com.v8n.modules.cart.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateCartRequest {

    @NotBlank(message = "Region ID is required")
    private UUID regionId;

    private UUID customerId;

    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Currency code is required")
    @Size(min = 3, max = 3, message = "Currency code must be exactly 3 characters")
    private String currencyCode;
}