package com.v8n.modules.order.domain.event;

import com.v8n.modules.core.domain.event.DomainEvent;
import com.v8n.modules.order.domain.entity.Order;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderPlacedEvent extends DomainEvent {
    private final UUID orderId;
    private final UUID customerId;
    private final String email;
    private final long total;

    public OrderPlacedEvent(Order order) {
        super(order);
        this.orderId = order.getId();
        this.customerId = order.getCustomer() != null ? order.getCustomer().getId() : null;
        this.email = order.getEmail();
        this.total = order.getTotal();
    }
}