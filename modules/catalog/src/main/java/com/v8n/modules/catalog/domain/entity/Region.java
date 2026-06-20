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

import java.math.BigDecimal;
import java.util.Map;

@Entity
@Table(name = "regions")
@Getter
@Setter
@NoArgsConstructor
public class Region extends BaseEntity {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "currency_code", nullable = false)
    private String currencyCode;

    @Column(name = "tax_rate", nullable = false, precision = 5, scale = 4)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Column(name = "tax_code", length = 50)
    private String taxCode;

    @jakarta.persistence.Transient
    private boolean automaticTaxes = true;

    @jakarta.persistence.Transient
    private boolean includesGst = false;

    @jakarta.persistence.Transient
    private String countryCode;

    @jakarta.persistence.Transient
    private boolean active = true;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;
}
