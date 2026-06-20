package com.v8n.modules.inventory.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reservation_items")
@Getter
@Setter
@NoArgsConstructor
public class ReservationItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    @Column(name = "line_item_id", nullable = false)
    private UUID lineItemId;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "RESERVED";

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    public boolean isExpired() {
        return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
    }
}
