package com.v8n.modules.cart.domain.event;

import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.cart.domain.entity.LineItem;
import com.v8n.modules.core.domain.event.DomainEvent;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ItemAddedToCartEvent extends DomainEvent {
    private final UUID cartId;
    private final UUID lineItemId;
    private final UUID variantId;
    private final int quantity;
    private final int unitPrice;

    public ItemAddedToCartEvent(Cart cart, LineItem item) {
        super(cart);
        this.cartId = cart.getId();
        this.lineItemId = item.getId();
        this.variantId = item.getVariant() != null ? item.getVariant().getId() : null;
        this.quantity = item.getQuantity();
        this.unitPrice = item.getUnitPrice();
    }
}