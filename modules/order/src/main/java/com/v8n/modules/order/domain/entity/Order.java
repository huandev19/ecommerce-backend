package com.v8n.modules.order.domain.entity;

import com.v8n.modules.catalog.domain.entity.Region;
import com.v8n.modules.core.domain.entity.BaseEntity;
import com.v8n.modules.identity.domain.entity.Address;
import com.v8n.modules.identity.domain.entity.Customer;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseEntity {

    @Column(name = "display_id", unique = true)
    private Long displayId;

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
    @NotBlank
    private String currencyCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipping_address_id")
    private Address shippingAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;

    @Convert(converter = OrderStatusConverter.class)
    @Column(name = "status", nullable = false, length = 50)
    private OrderStatus status = OrderStatus.PENDING;

    @Enumerated(EnumType.STRING)
    @Column(name = "fulfillment_status", nullable = false, length = 50)
    private FulfillmentStatus fulfillmentStatus = FulfillmentStatus.NOT_FULFILLED;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false, length = 50)
    private PaymentStatus paymentStatus = PaymentStatus.NOT_PAID;

    @Column(name = "subtotal", nullable = false)
    private int subtotal = 0; // Price in cents (DB stores as INTEGER)

    @Column(name = "discount_total", nullable = false)
    private int discountTotal = 0; // Price in cents

    @Column(name = "shipping_total", nullable = false)
    private int shippingTotal = 0; // Price in cents

    @Column(name = "tax_total", nullable = false)
    private int taxTotal = 0; // Price in cents

    @Column(name = "total", nullable = false)
    private int total = 0; // Price in cents

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderStatusHistory> statusHistory = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata = new HashMap<>();

    // ──────────────────────────────────────────────
    // Domain Business Methods
    // ──────────────────────────────────────────────

    /**
     * Add an item to the order and recalculate totals.
     */
    public void addItem(OrderItem item) {
        item.setOrder(this);
        items.add(item);
        recalculateTotals();
    }

    /**
     * Remove an item from the order and recalculate totals.
     */
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
        recalculateTotals();
    }

    /**
     * Recalculate subtotal = sum of item subtotals, then total = subtotal + tax + shipping - discount.
     */
    public void recalculateTotals() {
        long sumSubtotal = 0;
        for (OrderItem item : items) {
            sumSubtotal += item.getSubtotal();
        }
        this.subtotal = (int) sumSubtotal;
        this.total = this.subtotal + this.taxTotal + this.shippingTotal - this.discountTotal;
        if (this.total < 0) {
            this.total = 0;
        }
    }

    /**
     * Valid order status transitions.
     */
    private static final Set<OrderStatus> CANCELLABLE_STATUSES = Set.of(
            OrderStatus.PENDING,
            OrderStatus.CONFIRMED,
            OrderStatus.PROCESSING
    );

    /**
     * Cancel the order if it's in a cancellable state.
     *
     * @throws IllegalStateException if order cannot be cancelled
     */
    public void cancel() {
        if (status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }
        if (status == OrderStatus.DELIVERED || status == OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot cancel order in status: " + status);
        }
        this.status = OrderStatus.CANCELLED;
        this.canceledAt = LocalDateTime.now();
    }

    /**
     * Transition to a new status with audit trail.
     */
    public void transitionStatus(OrderStatus newStatus) {
        if (this.status == newStatus) {
            return;
        }
        // Basic state machine validation
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot transition from CANCELLED status");
        }
        if (this.status == OrderStatus.DELIVERED && newStatus != OrderStatus.RETURNED) {
            throw new IllegalStateException("Delivered order can only transition to RETURNED");
        }
        OrderStatus fromStatus = this.status;
        this.status = newStatus;
        // Add audit trail
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(this);
        history.setFromStatus(fromStatus);
        history.setToStatus(newStatus);
        this.statusHistory.add(history);
    }
}