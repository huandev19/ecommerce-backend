package com.v8n.modules.cart.domain.event;

import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.cart.domain.entity.LineItem;
import com.v8n.modules.core.domain.event.DomainEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ItemRemovedFromCartEvent extends DomainEvent {
    private final UUID cartId;
    private final UUID lineItemId;
    private final UUID variantId;

    public ItemRemovedFromCartEvent(Cart cart, LineItem item) {
        super(cart);
        this.cartId = cart.getId();
        this.lineItemId = item.getId();
        this.variantId = item.getVariant() != null ? item.getVariant().getId() : null;
    }
}