package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.ProductImage;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends BaseRepository<ProductImage, UUID> {

    List<ProductImage> findByProductIdOrderByDisplayOrderAsc(UUID productId);

    List<ProductImage> findByProductId(UUID productId);
}
