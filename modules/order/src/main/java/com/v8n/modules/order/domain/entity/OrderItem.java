package com.v8n.modules.order.domain.entity;

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
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
public class OrderItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @Column(name = "quantity", nullable = false)
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity = 1;

    @Column(name = "unit_price", nullable = false)
    @Min(value = 0, message = "Unit price must be non-negative")
    private int unitPrice = 0; // Price in cents (DB stores as INTEGER)

    @Column(name = "title", nullable = false, length = 255)
    @NotNull
    private String title;

    @Column(name = "sku", length = 100)
    private String sku;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata = new HashMap<>();

    public OrderItem(Order order, ProductVariant variant, int quantity, int unitPrice, String title) {
        this.order = order;
        this.variant = variant;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.title = title;
    }

    /**
     * Get subtotal in cents (unitPrice * quantity).
     */
    public long getSubtotal() {
        return (long) unitPrice * quantity;
    }

    /**
     * Get total in cents (unitPrice * quantity) — same as subtotal without tax/discount.
     */
    public long getTotal() {
        return getSubtotal();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (getId() == null) return false;
        OrderItem that = (OrderItem) o;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? Objects.hash(getId()) : super.hashCode();
    }
}