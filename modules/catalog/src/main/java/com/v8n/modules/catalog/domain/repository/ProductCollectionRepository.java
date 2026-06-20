package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.ProductCollection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductCollectionRepository extends BaseRepository<ProductCollection, UUID> {

    Optional<ProductCollection> findBySlug(String slug);

    List<ProductCollection> findByActiveTrueOrderByDisplayOrderAsc();

    boolean existsBySlug(String slug);
}
