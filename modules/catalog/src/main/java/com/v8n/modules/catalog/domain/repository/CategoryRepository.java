package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.Category;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends BaseRepository<Category, UUID> {

    Optional<Category> findBySlug(String slug);

    List<Category> findByParentCategoryIdIsNull();

    List<Category> findByParentCategoryIdOrderByDisplayOrderAsc(UUID parentCategoryId);

    List<Category> findByActiveTrueOrderByDisplayOrderAsc();

    boolean existsBySlug(String slug);
}
