package com.v8n.promotion.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
public class Discount extends BaseEntity {

    @Column(name = "code", unique = true, length = 100)
    private String code;

    @Column(name = "type", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    @NotNull
    private DiscountType type = DiscountType.PERCENTAGE;

    @Column(name = "value", nullable = false)
    private int value = 0;

    @Column(name = "currency_code", length = 3)
    private String currencyCode;

    @Column(name = "min_requirement_amount")
    private int minRequirementAmount = 0;

    @Column(name = "max_discount_amount")
    private int maxDiscountAmount = 0;

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @Column(name = "ends_at")
    private LocalDateTime endsAt;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "usage_count", nullable = false)
    private int usageCount = 0;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic = false;

    @Column(name = "description", length = 500)
    private String description;

    @OneToMany(mappedBy = "discount", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<DiscountRule> rules = new ArrayList<>();

    @OneToMany(mappedBy = "discount", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<DiscountCondition> conditions = new ArrayList<>();

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private java.util.Map<String, Object> metadata = new java.util.HashMap<>();

    public enum DiscountType {
        PERCENTAGE,
        FIXED_AMOUNT,
        FREE_SHIPPING
    }

    public boolean isValid() {
        if (!isActive) return false;
        LocalDateTime now = LocalDateTime.now();
        if (startsAt != null && now.isBefore(startsAt)) return false;
        if (endsAt != null && now.isAfter(endsAt)) return false;
        if (usageLimit != null && usageCount >= usageLimit) return false;
        return true;
    }

    public boolean isExpired() {
        return endsAt != null && LocalDateTime.now().isAfter(endsAt);
    }

    public void incrementUsage() {
        this.usageCount++;
    }

    public void addRule(DiscountRule rule) {
        rules.add(rule);
        rule.setDiscount(this);
    }

    public void addCondition(DiscountCondition condition) {
        conditions.add(condition);
        condition.setDiscount(this);
    }
}