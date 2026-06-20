package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.ProductOption;

import java.util.List;
import java.util.UUID;

public interface ProductOptionRepository extends BaseRepository<ProductOption, UUID> {

    List<ProductOption> findByProductId(UUID productId);

    List<ProductOption> findByProductIdOrderByCreatedAtAsc(UUID productId);
}