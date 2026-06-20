package com.v8n.modules.catalog.interfaces.rest;

import com.v8n.modules.catalog.application.dto.CategoryResponse;
import com.v8n.modules.catalog.application.dto.ProductResponse;
import com.v8n.modules.catalog.application.service.CatalogService;
import com.v8n.modules.catalog.domain.entity.ProductStatus;
import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.core.infrastructure.security.annotation.PublicEndpoint;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/store")
@RequiredArgsConstructor
@Tag(name = "Storefront Catalog API", description = "Public storefront APIs for products and categories")
public class StoreCatalogController {

    private final CatalogService catalogService;

    // ===== Product Endpoints =====

    @PublicEndpoint
    @Operation(summary = "Get published products")
    @GetMapping("/products")
    public ApiResponse<List<ProductResponse>> getPublishedProducts() {
        return ApiResponse.success(catalogService.getProductsByStatus(ProductStatus.published));
    }

    @PublicEndpoint
    @Operation(summary = "Get product by ID")
    @GetMapping("/products/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable UUID id) {
        ProductResponse product = catalogService.getProductById(id);
        return ApiResponse.success(product);
    }
    
    @PublicEndpoint
    @Operation(summary = "Get product by slug")
    @GetMapping("/products/slug/{slug}")
    public ApiResponse<ProductResponse> getProductBySlug(@PathVariable String slug) {
        ProductResponse product = catalogService.getProductBySlug(slug);
        return ApiResponse.success(product);
    }

    @PublicEndpoint
    @Operation(summary = "Get products by category")
    @GetMapping("/categories/{categoryId}/products")
    public ApiResponse<List<ProductResponse>> getProductsByCategory(@PathVariable UUID categoryId) {
        return ApiResponse.success(catalogService.getProductsByCategory(categoryId));
    }

    // ===== Category Endpoints =====

    @PublicEndpoint
    @Operation(summary = "Get root categories")
    @GetMapping("/categories")
    public ApiResponse<List<CategoryResponse>> getRootCategories() {
        return ApiResponse.success(catalogService.getRootCategories());
    }
    
    @PublicEndpoint
    @Operation(summary = "Get subcategories")
    @GetMapping("/categories/{parentId}/subcategories")
    public ApiResponse<List<CategoryResponse>> getSubcategories(@PathVariable UUID parentId) {
        return ApiResponse.success(catalogService.getSubcategories(parentId));
    }
}
