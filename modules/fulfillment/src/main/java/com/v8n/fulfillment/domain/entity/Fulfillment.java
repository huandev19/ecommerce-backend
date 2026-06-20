package com.v8n.fulfillment.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import com.v8n.modules.order.domain.entity.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fulfillments")
@Getter
@Setter
@NoArgsConstructor
public class Fulfillment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    private Order order;

    @Column(name = "display_id", unique = true)
    private Long displayId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private FulfillmentStatus status = FulfillmentStatus.NOT_FULFILLED;

    @Column(name = "shipped_at")
    private LocalDateTime shippedAt;

    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    @Column(name = "canceled_at")
    private LocalDateTime canceledAt;

    @Column(name = "tracking_number", length = 255)
    private String trackingNumber;

    @Column(name = "carrier", length = 100)
    private String carrier;

    @OneToMany(mappedBy = "fulfillment", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<FulfillmentItem> items = new ArrayList<>();

    public enum FulfillmentStatus {
        NOT_FULFILLED,
        PARTIALLY_FULFILLED,
        FULFILLED,
        PARTIALLY_RETURNED,
        RETURNED,
        CANCELLED
    }

    public void addItem(FulfillmentItem item) {
        items.add(item);
        item.setFulfillment(this);
    }

    public void removeItem(FulfillmentItem item) {
        items.remove(item);
        item.setFulfillment(null);
    }

    public boolean isFulfilled() {
        return status == FulfillmentStatus.FULFILLED;
    }

    public boolean isShipped() {
        return status == FulfillmentStatus.FULFILLED && shippedAt != null;
    }
}