package com.v8n.modules.cart.domain.entity;

import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
public class LineItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false)
    @NotNull
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    @NotNull
    private ProductVariant variant;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;

    @Column(name = "unit_price", nullable = false)
    @Min(value = 0, message = "Unit price must be non-negative")
    private int unitPrice = 0; // Price in cents (DB stores as INTEGER)

    @Column(name = "thumbnail", length = 500)
    private String thumbnail;

    @Column(name = "title", nullable = false, length = 255)
    @NotNull
    private String title;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata = new HashMap<>();

    public LineItem(Cart cart, ProductVariant variant, int quantity, int unitPrice, String title) {
        this.cart = cart;
        this.variant = variant;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.title = title;
    }

    /**
     * Get subtotal in cents (unitPrice * quantity)
     */
    public long getSubtotal() {
        return (long) unitPrice * quantity;
    }

    /**
     * Update quantity. Validates minimum value.
     */
    public void updateQuantity(int newQuantity) {
        if (newQuantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1, got: " + newQuantity);
        }
        this.quantity = newQuantity;
    }

    /**
     * Increase quantity by given amount.
     */
    public void increaseQuantity(int amount) {
        if (amount < 1) {
            throw new IllegalArgumentException("Increase amount must be positive, got: " + amount);
        }
        this.quantity += amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (getId() == null) return false;
        LineItem lineItem = (LineItem) o;
        return Objects.equals(getId(), lineItem.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? Objects.hash(getId()) : super.hashCode();
    }
}