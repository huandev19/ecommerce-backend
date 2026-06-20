package com.v8n.modules.catalog.interfaces.rest;

import com.v8n.modules.catalog.application.dto.CategoryRequest;
import com.v8n.modules.catalog.application.dto.CategoryResponse;
import com.v8n.modules.catalog.application.dto.ProductRequest;
import com.v8n.modules.catalog.application.dto.ProductResponse;
import com.v8n.modules.catalog.application.dto.VariantRequest;
import com.v8n.modules.catalog.application.dto.VariantResponse;
import com.v8n.modules.catalog.application.service.CatalogService;
import com.v8n.modules.core.application.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Catalog API", description = "Admin APIs for managing products, categories, variants, etc.")
public class AdminCatalogController {

    private final CatalogService catalogService;

    // ===== Product Endpoints =====

    @Operation(summary = "Get all products")
    @GetMapping("/products")
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.success(catalogService.getAllProducts());
    }

    @Operation(summary = "Get product by ID")
    @GetMapping("/products/{id}")
    public ApiResponse<ProductResponse> getProductById(@PathVariable UUID id) {
        return ApiResponse.success(catalogService.getProductById(id));
    }

    @Operation(summary = "Create product")
    @PostMapping("/products")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        return ApiResponse.success(catalogService.createProduct(request));
    }

    @Operation(summary = "Update product")
    @PutMapping("/products/{id}")
    public ApiResponse<ProductResponse> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductRequest request) {
        return ApiResponse.success(catalogService.updateProduct(id, request));
    }

    @Operation(summary = "Delete product")
    @DeleteMapping("/products/{id}")
    public ApiResponse<Void> deleteProduct(@PathVariable UUID id) {
        catalogService.deleteProduct(id);
        return ApiResponse.success(null);
    }

    // ===== Variant Endpoints =====

    @Operation(summary = "Get variants by product ID")
    @GetMapping("/products/{id}/variants")
    public ApiResponse<List<VariantResponse>> getVariantsByProductId(@PathVariable UUID id) {
        return ApiResponse.success(catalogService.getVariantsByProductId(id));
    }
    
    @Operation(summary = "Get variant by ID")
    @GetMapping("/variants/{id}")
    public ApiResponse<VariantResponse> getVariantById(@PathVariable UUID id) {
        return ApiResponse.success(catalogService.getVariantById(id));
    }

    @Operation(summary = "Create variant for product")
    @PostMapping("/products/{id}/variants")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<VariantResponse> createVariant(@PathVariable UUID id, @Valid @RequestBody VariantRequest request) {
        return ApiResponse.success(catalogService.createVariant(id, request));
    }

    @Operation(summary = "Update variant")
    @PutMapping("/variants/{id}")
    public ApiResponse<VariantResponse> updateVariant(@PathVariable UUID id, @Valid @RequestBody VariantRequest request) {
        return ApiResponse.success(catalogService.updateVariant(id, request));
    }

    @Operation(summary = "Delete variant")
    @DeleteMapping("/variants/{id}")
    public ApiResponse<Void> deleteVariant(@PathVariable UUID id) {
        catalogService.deleteVariant(id);
        return ApiResponse.success(null);
    }

    // ===== Category Endpoints =====

    @Operation(summary = "Get all categories")
    @GetMapping("/categories")
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return ApiResponse.success(catalogService.getAllCategories());
    }

    @Operation(summary = "Get category by ID")
    @GetMapping("/categories/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable UUID id) {
        return ApiResponse.success(catalogService.getCategoryById(id));
    }

    @Operation(summary = "Create category")
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        return ApiResponse.success(catalogService.createCategory(request));
    }

    @Operation(summary = "Update category")
    @PutMapping("/categories/{id}")
    public ApiResponse<CategoryResponse> updateCategory(@PathVariable UUID id, @Valid @RequestBody CategoryRequest request) {
        return ApiResponse.success(catalogService.updateCategory(id, request));
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/categories/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable UUID id) {
        catalogService.deleteCategory(id);
        return ApiResponse.success(null);
    }
}
