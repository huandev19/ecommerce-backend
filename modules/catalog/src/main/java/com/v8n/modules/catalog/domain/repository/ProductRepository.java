package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.Product;
import com.v8n.modules.catalog.domain.entity.ProductStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends BaseRepository<Product, UUID> {

    Optional<Product> findBySlug(String slug);

    List<Product> findByStatus(ProductStatus status);

    List<Product> findByCategoryId(UUID categoryId);

    List<Product> findByCollectionId(UUID collectionId);

    List<Product> findByTypeId(UUID typeId);

    boolean existsBySlug(String slug);
}
