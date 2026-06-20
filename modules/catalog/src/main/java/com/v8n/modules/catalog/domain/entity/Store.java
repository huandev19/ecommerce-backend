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
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
public class Store extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "default_currency_code")
    private String defaultCurrencyCode;

    @Column(name = "default_region_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private java.util.UUID defaultRegionId;

    @jakarta.persistence.Transient
    private String logoUrl;

    @jakarta.persistence.Transient
    private String contactEmail;

    @jakarta.persistence.Transient
    private String contactPhone;

    @jakarta.persistence.Transient
    private String address;

    @jakarta.persistence.Transient
    private boolean active = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;
}
