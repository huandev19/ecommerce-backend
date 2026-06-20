package com.v8n.promotion.application.dto;

import com.v8n.promotion.domain.entity.Discount.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRequest {
    private String code;
    private DiscountType type;
    private int value;
    private String currencyCode;
    private int minRequirementAmount;
    private int maxDiscountAmount;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private Integer usageLimit;
    private boolean isActive = true;
    private boolean isPublic;
    private String description;
}