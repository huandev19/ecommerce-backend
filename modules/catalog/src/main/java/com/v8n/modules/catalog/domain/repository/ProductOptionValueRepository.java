package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.ProductOptionValue;

import java.util.List;
import java.util.UUID;

public interface ProductOptionValueRepository extends BaseRepository<ProductOptionValue, UUID> {

    List<ProductOptionValue> findByOptionId(UUID optionId);

    List<ProductOptionValue> findByOptionIdOrderByCreatedAtAsc(UUID optionId);
}