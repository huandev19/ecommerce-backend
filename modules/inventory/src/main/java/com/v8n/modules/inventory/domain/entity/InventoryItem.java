package com.v8n.modules.inventory.domain.entity;

import com.v8n.modules.catalog.domain.entity.ProductVariant;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "inventory_items")
@Getter
@Setter
@NoArgsConstructor
public class InventoryItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id")
    private ProductVariant variant;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "warehouse_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID warehouseId;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity = 0;

    @Column(name = "incoming_quantity", nullable = false)
    private int incomingQuantity = 0;

    @Column(name = "location", length = 255)
    private String location;

    @Column(name = "overselling", nullable = false)
    private boolean overselling = false;

    @Column(name = "restock_threshold")
    private Integer restockThreshold = 0;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "requires_shipping", nullable = false)
    private boolean requiresShipping = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;

    public int getAvailableQuantity() {
        return quantity - reservedQuantity;
    }
}