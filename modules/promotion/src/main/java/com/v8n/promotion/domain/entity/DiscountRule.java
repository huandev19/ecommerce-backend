package com.v8n.promotion.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "discount_rules")
@Getter
@Setter
@NoArgsConstructor
public class DiscountRule extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", nullable = false)
    @NotNull
    private Discount discount;

    @Column(name = "rule_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private RuleType ruleType;

    @Column(name = "rule_value", nullable = false)
    private int ruleValue = 0;

    public enum RuleType {
        ITEMS_MIN_QUANTITY,
        ITEMS_MIN_SUBTOTAL,
        CUSTOMER_NEW,
        CUSTOMERReturning
    }
}