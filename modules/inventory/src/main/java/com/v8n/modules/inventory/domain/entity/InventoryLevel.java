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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "inventory_levels")
@Getter
@Setter
@NoArgsConstructor
public class InventoryLevel extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_item_id", nullable = false)
    private InventoryItem inventoryItem;

    @Column(name = "location_id", nullable = false)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID locationId;

    @Column(name = "stocked_quantity", nullable = false)
    private int stockedQuantity = 0;

    @Column(name = "reserved_quantity", nullable = false)
    private int reservedQuantity = 0;

    @Column(name = "incoming_quantity", nullable = false)
    private int incomingQuantity = 0;

    @Column(name = "delta_quantity")
    private int deltaQuantity = 0;

    @Column(name = "current_quantity")
    private int currentQuantity = 0;

    @Column(name = "reason", length = 255)
    private String reason;

    @Column(name = "note", length = 500)
    private String note;

    @Column(name = "reference_type", length = 100)
    private String referenceType;

    @Column(name = "reference_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID referenceId;

    @Column(name = "created_by")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID createdBy;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;
}