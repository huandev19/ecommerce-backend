package com.v8n.modules.cart.domain.event;

import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.core.domain.event.DomainEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class CartCreatedEvent extends DomainEvent {
    private final UUID cartId;
    private final UUID customerId;
    private final String email;

    public CartCreatedEvent(Cart cart) {
        super(cart);
        this.cartId = cart.getId();
        this.customerId = cart.getCustomer() != null ? cart.getCustomer().getId() : null;
        this.email = cart.getEmail();
    }
}