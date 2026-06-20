package com.v8n.modules.catalog.application.service;

import com.v8n.modules.catalog.application.dto.ProductRequest;
import com.v8n.modules.catalog.application.dto.ProductResponse;
import com.v8n.modules.catalog.application.dto.CategoryRequest;
import com.v8n.modules.catalog.application.dto.CategoryResponse;
import com.v8n.modules.catalog.application.dto.VariantRequest;
import com.v8n.modules.catalog.application.dto.VariantResponse;
import com.v8n.modules.catalog.domain.entity.Product;
import com.v8n.modules.catalog.domain.entity.ProductStatus;
import com.v8n.modules.catalog.domain.entity.Category;
import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.catalog.domain.repository.ProductRepository;
import com.v8n.modules.catalog.domain.repository.CategoryRepository;
import com.v8n.modules.catalog.domain.repository.ProductVariantRepository;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductVariantRepository variantRepository;

    // ===== Product Methods =====

    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID productId) {
        Product product = findProductById(productId);
        return toProductResponse(product);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductBySlug(String slug) {
        Product product = productRepository.findBySlug(slug)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
        return toProductResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByStatus(ProductStatus status) {
        return productRepository.findByStatus(status).stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(UUID categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setTitle(request.getTitle());
        product.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getTitle()));
        product.setSubtitle(request.getSubtitle());
        product.setDescription(request.getDescription());
        product.setThumbnailUrl(request.getThumbnailUrl());
        product.setStatus(request.getStatus() != null ? request.getStatus() : ProductStatus.draft);
        product.setDiscountable(request.isDiscountable());
        product.setOriginCountry(request.getOriginCountry());
        product.setWeight(request.getWeight());
        product.setHeight(request.getHeight());
        product.setWidth(request.getWidth());
        product.setLength(request.getLength());
        product.setHsCode(request.getHsCode());
        product.setMaterial(request.getMaterial());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdNotDeleted(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            product.setCategory(category);
        }

        product = productRepository.save(product);
        log.info("Created product: {}", product.getId());
        return toProductResponse(product);
    }

    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductRequest request) {
        Product product = findProductById(productId);

        if (request.getTitle() != null) product.setTitle(request.getTitle());
        if (request.getSlug() != null) product.setSlug(request.getSlug());
        if (request.getSubtitle() != null) product.setSubtitle(request.getSubtitle());
        if (request.getDescription() != null) product.setDescription(request.getDescription());
        if (request.getThumbnailUrl() != null) product.setThumbnailUrl(request.getThumbnailUrl());
        if (request.getStatus() != null) product.setStatus(request.getStatus());
        if (request.isDiscountable()) product.setDiscountable(request.isDiscountable());
        if (request.getOriginCountry() != null) product.setOriginCountry(request.getOriginCountry());
        if (request.getWeight() != null) product.setWeight(request.getWeight());
        if (request.getHeight() != null) product.setHeight(request.getHeight());
        if (request.getWidth() != null) product.setWidth(request.getWidth());
        if (request.getLength() != null) product.setLength(request.getLength());
        if (request.getHsCode() != null) product.setHsCode(request.getHsCode());
        if (request.getMaterial() != null) product.setMaterial(request.getMaterial());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findByIdNotDeleted(request.getCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            product.setCategory(category);
        }

        product = productRepository.save(product);
        log.info("Updated product: {}", product.getId());
        return toProductResponse(product);
    }

    @Transactional
    public void deleteProduct(UUID productId) {
        Product product = findProductById(productId);
        productRepository.delete(product);
        log.info("Deleted product: {}", productId);
    }

    // ===== Category Methods =====

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID categoryId) {
        Category category = findCategoryById(categoryId);
        return toCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.findByParentCategoryIdIsNull().stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getSubcategories(UUID parentId) {
        return categoryRepository.findByParentCategoryIdOrderByDisplayOrderAsc(parentId).stream()
                .map(this::toCategoryResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(request.getSlug() != null ? request.getSlug() : generateSlug(request.getName()));
        category.setDescription(request.getDescription());
        category.setActive(request.isActive());

        if (request.getParentCategoryId() != null) {
            Category parent = categoryRepository.findByIdNotDeleted(request.getParentCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParentCategory(parent);
        }

        category = categoryRepository.save(category);
        log.info("Created category: {}", category.getId());
        return toCategoryResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest request) {
        Category category = findCategoryById(categoryId);

        if (request.getName() != null) category.setName(request.getName());
        if (request.getSlug() != null) category.setSlug(request.getSlug());
        if (request.getDescription() != null) category.setDescription(request.getDescription());
        if (request.isActive()) category.setActive(request.isActive());

        if (request.getParentCategoryId() != null) {
            Category parent = categoryRepository.findByIdNotDeleted(request.getParentCategoryId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
            category.setParentCategory(parent);
        }

        category = categoryRepository.save(category);
        log.info("Updated category: {}", category.getId());
        return toCategoryResponse(category);
    }

    @Transactional
    public void deleteCategory(UUID categoryId) {
        Category category = findCategoryById(categoryId);
        categoryRepository.delete(category);
        log.info("Deleted category: {}", categoryId);
    }

    // ===== Variant Methods =====

    @Transactional(readOnly = true)
    public VariantResponse getVariantById(UUID variantId) {
        ProductVariant variant = findVariantById(variantId);
        return toVariantResponse(variant);
    }

    @Transactional(readOnly = true)
    public List<VariantResponse> getVariantsByProductId(UUID productId) {
        return variantRepository.findByProductId(productId).stream()
                .map(this::toVariantResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public VariantResponse createVariant(UUID productId, VariantRequest request) {
        Product product = findProductById(productId);

        ProductVariant variant = new ProductVariant();
        variant.setProduct(product);
        variant.setTitle(request.getTitle());
        variant.setSku(request.getSku());
        variant.setBarcode(request.getBarcode());
        variant.setEan(request.getEan());
        variant.setUpc(request.getUpc());
        variant.setInventoryQuantity(request.getInventoryQuantity());
        variant.setAllowBackorder(request.isAllowBackorder());
        variant.setManageInventory(request.isManageInventory());
        variant.setWeight(request.getWeight());
        variant.setHeight(request.getHeight());
        variant.setWidth(request.getWidth());
        variant.setLength(request.getLength());

        variant = variantRepository.save(variant);
        log.info("Created variant: {} for product: {}", variant.getId(), productId);
        return toVariantResponse(variant);
    }

    @Transactional
    public VariantResponse updateVariant(UUID variantId, VariantRequest request) {
        ProductVariant variant = findVariantById(variantId);

        if (request.getTitle() != null) variant.setTitle(request.getTitle());
        if (request.getSku() != null) variant.setSku(request.getSku());
        if (request.getBarcode() != null) variant.setBarcode(request.getBarcode());
        if (request.getEan() != null) variant.setEan(request.getEan());
        if (request.getUpc() != null) variant.setUpc(request.getUpc());
        if (request.getInventoryQuantity() > 0) variant.setInventoryQuantity(request.getInventoryQuantity());
        variant.setAllowBackorder(request.isAllowBackorder());
        variant.setManageInventory(request.isManageInventory());
        if (request.getWeight() != null) variant.setWeight(request.getWeight());
        if (request.getHeight() != null) variant.setHeight(request.getHeight());
        if (request.getWidth() != null) variant.setWidth(request.getWidth());
        if (request.getLength() != null) variant.setLength(request.getLength());

        variant = variantRepository.save(variant);
        log.info("Updated variant: {}", variant.getId());
        return toVariantResponse(variant);
    }

    @Transactional
    public void deleteVariant(UUID variantId) {
        ProductVariant variant = findVariantById(variantId);
        variantRepository.delete(variant);
        log.info("Deleted variant: {}", variantId);
    }

    // ===== Private Helper Methods =====

    private Product findProductById(UUID productId) {
        return productRepository.findByIdNotDeleted(productId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private Category findCategoryById(UUID categoryId) {
        return categoryRepository.findByIdNotDeleted(categoryId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CATEGORY_NOT_FOUND));
    }

    private ProductVariant findVariantById(UUID variantId) {
        return variantRepository.findByIdNotDeleted(variantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.VARIANT_NOT_FOUND));
    }

    private String generateSlug(String title) {
        if (title == null) return UUID.randomUUID().toString();
        return title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-+", "-")
                .trim();
    }

    private ProductResponse toProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .subtitle(product.getSubtitle())
                .description(product.getDescription())
                .slug(product.getSlug())
                .thumbnailUrl(product.getThumbnailUrl())
                .status(product.getStatus())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .originCountry(product.getOriginCountry())
                .discountable(product.isDiscountable())
                .weight(product.getWeight())
                .height(product.getHeight())
                .width(product.getWidth())
                .length(product.getLength())
                .hsCode(product.getHsCode())
                .material(product.getMaterial())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .build();
    }

    private CategoryResponse toCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .parentCategoryId(category.getParentCategory() != null ? category.getParentCategory().getId() : null)
                .active(category.isActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }

    private VariantResponse toVariantResponse(ProductVariant variant) {
        return VariantResponse.builder()
                .id(variant.getId())
                .productId(variant.getProduct().getId())
                .title(variant.getTitle())
                .sku(variant.getSku())
                .barcode(variant.getBarcode())
                .ean(variant.getEan())
                .upc(variant.getUpc())
                .inventoryQuantity(variant.getInventoryQuantity())
                .allowBackorder(variant.isAllowBackorder())
                .manageInventory(variant.isManageInventory())
                .weight(variant.getWeight())
                .height(variant.getHeight())
                .width(variant.getWidth())
                .length(variant.getLength())
                .createdAt(variant.getCreatedAt())
                .updatedAt(variant.getUpdatedAt())
                .build();
    }
}