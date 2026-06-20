package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.Store;

import java.util.Optional;
import java.util.UUID;

public interface StoreRepository extends BaseRepository<Store, UUID> {

    Optional<Store> findByName(String name);

    boolean existsByName(String name);
}