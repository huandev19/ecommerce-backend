package com.v8n.fulfillment.domain.entity;

import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fulfillment_items")
@Getter
@Setter
@NoArgsConstructor
public class FulfillmentItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fulfillment_id", nullable = false)
    @NotNull
    private Fulfillment fulfillment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    @NotNull
    private ProductVariant variant;

    @Column(name = "title", nullable = false, length = 255)
    @NotNull
    private String title;

    @Column(name = "variant_title", length = 255)
    private String variantTitle;

    @Column(name = "sku", length = 100)
    private String sku;

    @Column(name = "quantity", nullable = false)
    private int quantity = 0;

    @Column(name = "fulfilled_quantity", nullable = false)
    private int fulfilledQuantity = 0;

    @Column(name = "returned_quantity", nullable = false)
    private int returnedQuantity = 0;
}