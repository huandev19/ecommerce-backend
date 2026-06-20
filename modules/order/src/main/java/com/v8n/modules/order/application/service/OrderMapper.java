package com.v8n.modules.order.application.service;

import com.v8n.modules.order.application.dto.OrderItemResponse;
import com.v8n.modules.order.application.dto.OrderResponse;
import com.v8n.modules.order.application.dto.OrderStatusHistoryResponse;
import com.v8n.modules.order.domain.entity.Order;
import com.v8n.modules.order.domain.entity.OrderItem;
import com.v8n.modules.order.domain.entity.OrderStatusHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        OrderResponse.OrderResponseBuilder builder = OrderResponse.builder()
                .id(order.getId())
                .displayId(order.getDisplayId())
                .regionId(order.getRegion() != null ? order.getRegion().getId() : null)
                .customerId(order.getCustomer() != null ? order.getCustomer().getId() : null)
                .email(order.getEmail())
                .currencyCode(order.getCurrencyCode())
                .shippingAddressId(order.getShippingAddress() != null ? order.getShippingAddress().getId() : null)
                .billingAddressId(order.getBillingAddress() != null ? order.getBillingAddress().getId() : null)
                .status(order.getStatus() != null ? order.getStatus().name() : null)
                .fulfillmentStatus(order.getFulfillmentStatus() != null ? order.getFulfillmentStatus().name() : null)
                .paymentStatus(order.getPaymentStatus() != null ? order.getPaymentStatus().name() : null)
                .subtotal(order.getSubtotal())
                .discountTotal(order.getDiscountTotal())
                .shippingTotal(order.getShippingTotal())
                .taxTotal(order.getTaxTotal())
                .total(order.getTotal())
                .canceledAt(order.getCanceledAt())
                .metadata(order.getMetadata())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt());

        // Map items
        if (order.getItems() != null) {
            builder.items(order.getItems().stream()
                    .map(this::toOrderItemResponse)
                    .toList());
        }

        // Map status history
        if (order.getStatusHistory() != null) {
            builder.statusHistory(order.getStatusHistory().stream()
                    .map(this::toStatusHistoryResponse)
                    .toList());
        }

        return builder.build();
    }

    public OrderItemResponse toOrderItemResponse(OrderItem item) {
        if (item == null) {
            return null;
        }

        return OrderItemResponse.builder()
                .id(item.getId())
                .variantId(item.getVariant() != null ? item.getVariant().getId() : null)
                .title(item.getTitle())
                .sku(item.getVariant() != null ? item.getVariant().getSku() : null)
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal((int) item.getSubtotal())
                .metadata(item.getMetadata())
                .build();
    }

    public OrderStatusHistoryResponse toStatusHistoryResponse(OrderStatusHistory history) {
        if (history == null) {
            return null;
        }

        return OrderStatusHistoryResponse.builder()
                .id(history.getId())
                .fromStatus(history.getFromStatus() != null ? history.getFromStatus().name() : null)
                .toStatus(history.getToStatus() != null ? history.getToStatus().name() : null)
                .createdAt(history.getCreatedAt())
                .build();
    }
}