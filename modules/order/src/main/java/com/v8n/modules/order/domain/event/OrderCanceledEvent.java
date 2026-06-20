package com.v8n.modules.order.domain.event;

import com.v8n.modules.core.domain.event.DomainEvent;
import com.v8n.modules.order.domain.entity.Order;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderCanceledEvent extends DomainEvent {
    private final UUID orderId;
    private final UUID customerId;

    public OrderCanceledEvent(Order order) {
        super(order);
        this.orderId = order.getId();
        this.customerId = order.getCustomer() != null ? order.getCustomer().getId() : null;
    }
}