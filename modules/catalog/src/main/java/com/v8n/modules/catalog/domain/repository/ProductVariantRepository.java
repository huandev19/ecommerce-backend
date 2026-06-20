package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.ProductVariant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductVariantRepository extends BaseRepository<ProductVariant, UUID> {

    List<ProductVariant> findByProductId(UUID productId);

    Optional<ProductVariant> findBySku(String sku);

    boolean existsBySku(String sku);
}
