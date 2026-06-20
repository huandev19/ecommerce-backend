package com.v8n.modules.order.domain.event;

import com.v8n.modules.core.domain.event.DomainEvent;
import com.v8n.modules.order.domain.entity.Order;
import com.v8n.modules.order.domain.entity.OrderStatus;
import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderStatusChangedEvent extends DomainEvent {
    private final UUID orderId;
    private final OrderStatus previousStatus;
    private final OrderStatus newStatus;

    public OrderStatusChangedEvent(Order order, OrderStatus previousStatus) {
        super(order);
        this.orderId = order.getId();
        this.previousStatus = previousStatus;
        this.newStatus = order.getStatus();
    }
}