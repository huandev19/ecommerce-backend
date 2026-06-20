package com.v8n.modules.cart.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.v8n.modules.identity.application.dto.AddressResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CartResponse {

    private UUID id;
    private UUID regionId;
    private UUID customerId;
    private String email;
    private String currencyCode;
    private AddressResponse shippingAddress;
    private AddressResponse billingAddress;
    private LocalDateTime completedAt;
    private List<LineItemResponse> lineItems;
    private long subtotal; // In cents
    private int itemCount;
    private boolean completed;
    private boolean empty;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}