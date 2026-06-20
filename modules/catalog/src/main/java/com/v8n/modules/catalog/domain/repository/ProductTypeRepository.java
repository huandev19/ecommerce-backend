package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.ProductType;

import java.util.Optional;
import java.util.UUID;

public interface ProductTypeRepository extends BaseRepository<ProductType, UUID> {

    Optional<ProductType> findByName(String name);

    Optional<ProductType> findBySlug(String slug);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);
}
