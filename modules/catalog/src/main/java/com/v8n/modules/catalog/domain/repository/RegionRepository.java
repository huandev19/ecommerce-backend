package com.v8n.modules.catalog.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.catalog.domain.entity.Region;

import java.util.Optional;
import java.util.UUID;

public interface RegionRepository extends BaseRepository<Region, UUID> {

    Optional<Region> findByName(String name);

    Optional<Region> findByCurrencyCode(String currencyCode);

    boolean existsByName(String name);
}