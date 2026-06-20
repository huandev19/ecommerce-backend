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

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "discount_conditions")
@Getter
@Setter
@NoArgsConstructor
public class DiscountCondition extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id", nullable = false)
    @NotNull
    private Discount discount;

    @Column(name = "condition_type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private ConditionType conditionType;

    @Column(name = "operator", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    @NotNull
    private Operator operator;

    @Column(name = "condition_value", nullable = false)
    private String conditionValue;

    public enum ConditionType {
        PRODUCT,
        COLLECTION,
        CATEGORY,
        TAG,
        CUSTOMER_GROUP
    }

    public enum Operator {
        IN,
        NOT_IN,
        EQ,
        NEQ,
        GT,
        GTE,
        LT,
        LTE
    }
}