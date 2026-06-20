package com.v8n.modules.catalog.domain.entity;

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

@Entity
@Table(name = "product_variants")
@Getter
@Setter
@NoArgsConstructor
public class ProductVariant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "barcode", length = 100)
    private String barcode;

    @Column(name = "ean", length = 100)
    private String ean;

    @Column(name = "upc", length = 100)
    private String upc;

    @Column(name = "inventory_quantity", nullable = false)
    private int inventoryQuantity = 0;

    @Column(name = "allow_backorder", nullable = false)
    private boolean allowBackorder = false;

    @Column(name = "manage_inventory", nullable = false)
    private boolean manageInventory = true;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "length")
    private Integer length;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;
}
