package com.v8n.promotion.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.promotion.application.dto.DiscountRequest;
import com.v8n.promotion.application.dto.DiscountResponse;
import com.v8n.promotion.application.dto.DiscountValidationRequest;
import com.v8n.promotion.domain.entity.Discount;
import com.v8n.promotion.domain.entity.Discount.DiscountType;
import com.v8n.promotion.domain.repository.DiscountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PromotionService {

    private final DiscountRepository discountRepository;

    // ===== Discount CRUD Methods =====

    @Transactional(readOnly = true)
    public DiscountResponse getDiscountById(UUID discountId) {
        Discount discount = findDiscountById(discountId);
        return toDiscountResponse(discount);
    }

    @Transactional(readOnly = true)
    public DiscountResponse getDiscountByCode(String code) {
        Discount discount = discountRepository.findByCodeNotDeleted(code)
                .orElseThrow(() -> new BusinessException(ErrorCode.DISCOUNT_NOT_FOUND));
        return toDiscountResponse(discount);
    }

    @Transactional(readOnly = true)
    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll().stream()
                .map(this::toDiscountResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<DiscountResponse> getActiveDiscounts() {
        return discountRepository.findAllActive().stream()
                .map(this::toDiscountResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiscountResponse createDiscount(DiscountRequest request) {
        if (request.getCode() != null && discountRepository.findByCodeNotDeleted(request.getCode()).isPresent()) {
            throw new BusinessException(ErrorCode.DISCOUNT_INVALID, "Discount code already exists");
        }

        Discount discount = new Discount();
        discount.setCode(request.getCode());
        discount.setType(request.getType() != null ? request.getType() : DiscountType.PERCENTAGE);
        discount.setValue(request.getValue());
        discount.setCurrencyCode(request.getCurrencyCode());
        discount.setMinRequirementAmount(request.getMinRequirementAmount());
        discount.setMaxDiscountAmount(request.getMaxDiscountAmount());
        discount.setStartsAt(request.getStartsAt());
        discount.setEndsAt(request.getEndsAt());
        discount.setUsageLimit(request.getUsageLimit());
        discount.setActive(request.isActive());
        discount.setPublic(request.isPublic());
        discount.setDescription(request.getDescription());

        discount = discountRepository.save(discount);
        log.info("Created discount: {} with code: {}", discount.getId(), discount.getCode());
        return toDiscountResponse(discount);
    }

    @Transactional
    public DiscountResponse updateDiscount(UUID discountId, DiscountRequest request) {
        Discount discount = findDiscountById(discountId);

        if (request.getCode() != null && !request.getCode().equals(discount.getCode())) {
            discountRepository.findByCodeNotDeleted(request.getCode()).ifPresent(existing -> {
                throw new BusinessException(ErrorCode.DISCOUNT_INVALID, "Discount code already exists");
            });
            discount.setCode(request.getCode());
        }
        if (request.getType() != null) discount.setType(request.getType());
        if (request.getValue() > 0) discount.setValue(request.getValue());
        if (request.getCurrencyCode() != null) discount.setCurrencyCode(request.getCurrencyCode());
        if (request.getMinRequirementAmount() > 0) discount.setMinRequirementAmount(request.getMinRequirementAmount());
        if (request.getMaxDiscountAmount() > 0) discount.setMaxDiscountAmount(request.getMaxDiscountAmount());
        if (request.getStartsAt() != null) discount.setStartsAt(request.getStartsAt());
        if (request.getEndsAt() != null) discount.setEndsAt(request.getEndsAt());
        if (request.getUsageLimit() != null) discount.setUsageLimit(request.getUsageLimit());
        discount.setActive(request.isActive());
        discount.setPublic(request.isPublic());
        if (request.getDescription() != null) discount.setDescription(request.getDescription());

        discount = discountRepository.save(discount);
        log.info("Updated discount: {}", discountId);
        return toDiscountResponse(discount);
    }

    @Transactional
    public void deleteDiscount(UUID discountId) {
        Discount discount = findDiscountById(discountId);
        discountRepository.delete(discount);
        log.info("Deleted discount: {}", discountId);
    }

    @Transactional
    public void deactivateDiscount(UUID discountId) {
        Discount discount = findDiscountById(discountId);
        discount.setActive(false);
        discountRepository.save(discount);
        log.info("Deactivated discount: {}", discountId);
    }

    // ===== Discount Validation & Calculation =====

    @Transactional(readOnly = true)
    public DiscountResponse validateDiscount(DiscountValidationRequest request) {
        String code = request.getCode();
        int cartSubtotal = request.getSubtotal();

        Discount discount = discountRepository.findValidByCode(code, LocalDateTime.now())
                .orElseThrow(() -> new BusinessException(ErrorCode.DISCOUNT_NOT_FOUND));

        if (!discount.isValid()) {
            if (discount.isExpired()) {
                throw new BusinessException(ErrorCode.DISCOUNT_EXPIRED);
            }
            if (discount.getUsageLimit() != null && discount.getUsageCount() >= discount.getUsageLimit()) {
                throw new BusinessException(ErrorCode.DISCOUNT_USAGE_LIMIT_REACHED);
            }
            throw new BusinessException(ErrorCode.DISCOUNT_INVALID);
        }

        if (discount.getMinRequirementAmount() > 0 && cartSubtotal < discount.getMinRequirementAmount()) {
            throw new BusinessException(ErrorCode.DISCOUNT_INVALID,
                    "Minimum subtotal of " + discount.getMinRequirementAmount() + " required");
        }

        return toDiscountResponse(discount);
    }

    @Transactional(readOnly = true)
    public int calculateDiscount(String code, int subtotal) {
        Discount discount = discountRepository.findValidByCode(code, LocalDateTime.now())
                .orElseThrow(() -> new BusinessException(ErrorCode.DISCOUNT_NOT_FOUND));

        if (!discount.isValid()) {
            return 0;
        }

        int discountAmount = 0;

        switch (discount.getType()) {
            case PERCENTAGE:
                discountAmount = (int) ((long) subtotal * discount.getValue() / 100);
                break;
            case FIXED_AMOUNT:
                discountAmount = discount.getValue();
                break;
            case FREE_SHIPPING:
                discountAmount = 0;
                break;
        }

        if (discount.getMaxDiscountAmount() > 0 && discountAmount > discount.getMaxDiscountAmount()) {
            discountAmount = discount.getMaxDiscountAmount();
        }

        if (discountAmount > subtotal) {
            discountAmount = subtotal;
        }

        return discountAmount;
    }

    @Transactional
    public void incrementUsage(UUID discountId) {
        Discount discount = findDiscountById(discountId);
        discount.incrementUsage();
        discountRepository.save(discount);
    }

    // ===== Private Helper Methods =====

    private Discount findDiscountById(UUID discountId) {
        return discountRepository.findByIdNotDeleted(discountId)
                .orElseThrow(() -> new BusinessException(ErrorCode.DISCOUNT_NOT_FOUND));
    }

    private DiscountResponse toDiscountResponse(Discount discount) {
        return DiscountResponse.builder()
                .id(discount.getId())
                .code(discount.getCode())
                .type(discount.getType())
                .value(discount.getValue())
                .currencyCode(discount.getCurrencyCode())
                .minRequirementAmount(discount.getMinRequirementAmount())
                .maxDiscountAmount(discount.getMaxDiscountAmount())
                .startsAt(discount.getStartsAt())
                .endsAt(discount.getEndsAt())
                .usageLimit(discount.getUsageLimit())
                .usageCount(discount.getUsageCount())
                .isActive(discount.isActive())
                .isPublic(discount.isPublic())
                .description(discount.getDescription())
                .createdAt(discount.getCreatedAt())
                .updatedAt(discount.getUpdatedAt())
                .build();
    }
}