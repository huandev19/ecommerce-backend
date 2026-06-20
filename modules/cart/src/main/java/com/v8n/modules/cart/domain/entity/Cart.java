package com.v8n.modules.cart.domain.entity;

import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.catalog.domain.entity.Region;
import com.v8n.modules.core.domain.entity.BaseEntity;
import com.v8n.modules.identity.domain.entity.Address;
import com.v8n.modules.identity.domain.entity.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
public class Cart extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id", nullable = false)
    @NotNull
    private Region region;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "email", length = 255)
    @Email
    private String email;

    @Column(name = "currency_code", nullable = false, length = 3)
    @NotNull
    @Size(min = 3, max = 3)
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineItem> lineItems = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata = new HashMap<>();

    // ---- Business Methods ----

    /**
     * Add an item to the cart. If the same variant already exists, increase its quantity.
     */
    public void addItem(ProductVariant variant, int quantity, int unitPrice, String title, String thumbnail) {
        if (completedAt != null) {
            throw new IllegalStateException("Cannot add items to a completed cart");
        }
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        Optional<LineItem> existingItem = findItemByVariantId(variant.getId());
        if (existingItem.isPresent()) {
            existingItem.get().increaseQuantity(quantity);
        } else {
            LineItem lineItem = new LineItem(this, variant, quantity, unitPrice, title);
            lineItem.setThumbnail(thumbnail);
            this.lineItems.add(lineItem);
        }
    }

    /**
     * Remove an item from the cart by variant ID.
     */
    public boolean removeItemByVariantId(UUID variantId) {
        Iterator<LineItem> iterator = lineItems.iterator();
        while (iterator.hasNext()) {
            LineItem item = iterator.next();
            if (item.getVariant().getId().equals(variantId)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    /**
     * Remove a specific line item.
     */
    public boolean removeItem(LineItem lineItem) {
        return lineItems.remove(lineItem);
    }

    /**
     * Clear all items from the cart.
     */
    public void clearItems() {
        lineItems.clear();
    }

    /**
     * Find a line item by variant ID.
     */
    public Optional<LineItem> findItemByVariant(UUID variantId) {
        return findItemByVariantId(variantId);
    }

    private Optional<LineItem> findItemByVariantId(UUID variantId) {
        return lineItems.stream()
                .filter(item -> item.getVariant().getId().equals(variantId))
                .findFirst();
    }

    /**
     * Get the total number of items (sum of quantities).
     */
    public int getItemCount() {
        return lineItems.stream().mapToInt(LineItem::getQuantity).sum();
    }

    /**
     * Get the total subtotal in cents (sum of line item subtotals).
     */
    public long getSubtotal() {
        return lineItems.stream().mapToLong(LineItem::getSubtotal).sum();
    }

    /**
     * Mark the cart as completed.
     */
    public void markCompleted() {
        this.completedAt = LocalDateTime.now();
    }

    /**
     * Check if the cart is completed.
     */
    public boolean isCompleted() {
        return completedAt != null;
    }

    /**
     * Check if the cart is empty (has no items).
     */
    public boolean isEmpty() {
        return lineItems.isEmpty();
    }
}