package com.v8n.promotion.application.dto;

import com.v8n.promotion.domain.entity.Discount.DiscountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {
    private UUID id;
    private String code;
    private DiscountType type;
    private int value;
    private String currencyCode;
    private int minRequirementAmount;
    private int maxDiscountAmount;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;
    private Integer usageLimit;
    private int usageCount;
    private boolean isActive;
    private boolean isPublic;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}