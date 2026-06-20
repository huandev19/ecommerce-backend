package com.v8n.modules.catalog.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "product_collections")
@Getter
@Setter
@NoArgsConstructor
public class ProductCollection extends BaseEntity {

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "handle", nullable = false, length = 255)
    private String slug;

    @jakarta.persistence.Transient
    private String description;

    @jakarta.persistence.Transient
    private String imageUrl;

    @Column(name = "rank", nullable = false)
    private int displayOrder = 0;

    @Column(name = "is_active", nullable = false)
    private boolean active = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;

}
