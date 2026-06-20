package com.v8n.modules.catalog.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "title", nullable = false, length = 500)
    private String title;

    @Column(name = "subtitle", length = 500)
    private String subtitle;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "handle", nullable = false, length = 255)
    private String slug;

    @Column(name = "handle", nullable = false, length = 255, insertable = false, updatable = false)
    private String handle;

    @Column(name = "thumbnail", length = 500)
    private String thumbnailUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private ProductStatus status = ProductStatus.draft;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "collection_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID collectionId;

    @Column(name = "type_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UUID typeId;

    @Column(name = "origin_country", length = 2)
    private String originCountry;

    @Column(name = "discountable", nullable = false)
    private boolean discountable = true;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "length")
    private Integer length;

    @Column(name = "hs_code", length = 50)
    private String hsCode;

    @Column(name = "mid_code", length = 50)
    private String midCode;

    @Column(name = "material", length = 255)
    private String material;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;
}
