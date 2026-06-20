package com.v8n.promotion.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.promotion.application.dto.DiscountRequest;
import com.v8n.promotion.application.dto.DiscountResponse;
import com.v8n.promotion.application.service.PromotionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/discounts")
@RequiredArgsConstructor
@Tag(name = "Admin Promotion API", description = "Admin APIs for managing discounts and promotions")
public class AdminDiscountController {

    private final PromotionService promotionService;

    @Operation(summary = "Get all discounts")
    @GetMapping
    public ApiResponse<List<DiscountResponse>> getAllDiscounts() {
        return ApiResponse.success(promotionService.getAllDiscounts());
    }

    @Operation(summary = "Create discount")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DiscountResponse> createDiscount(@Valid @RequestBody DiscountRequest request) {
        return ApiResponse.success(promotionService.createDiscount(request));
    }

    @Operation(summary = "Update discount")
    @PutMapping("/{id}")
    public ApiResponse<DiscountResponse> updateDiscount(@PathVariable UUID id, @Valid @RequestBody DiscountRequest request) {
        return ApiResponse.success(promotionService.updateDiscount(id, request));
    }

    @Operation(summary = "Delete discount")
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDiscount(@PathVariable UUID id) {
        promotionService.deleteDiscount(id);
        return ApiResponse.success(null);
    }
}
