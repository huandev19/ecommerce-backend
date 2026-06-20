package com.v8n.modules.order.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest {

    @NotNull
    private UUID cartId;

    @NotNull
    private UUID regionId;

    private UUID customerId;

    @Email
    private String email;

    @NotBlank
    private String currencyCode;

    private UUID shippingAddressId;

    private UUID billingAddressId;
}