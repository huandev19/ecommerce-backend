package com.v8n.modules.order.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderResponse {
    private UUID id;
    private Long displayId;
    private UUID regionId;
    private UUID customerId;
    private String email;
    private String currencyCode;
    private UUID shippingAddressId;
    private UUID billingAddressId;
    private String status;
    private String fulfillmentStatus;
    private String paymentStatus;
    private long subtotal;
    private long discountTotal;
    private long shippingTotal;
    private long taxTotal;
    private long total;
    private LocalDateTime canceledAt;
    private List<OrderItemResponse> items;
    private List<OrderStatusHistoryResponse> statusHistory;
    private Map<String, Object> metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}