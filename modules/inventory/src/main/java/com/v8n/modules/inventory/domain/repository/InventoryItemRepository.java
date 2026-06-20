package com.v8n.modules.inventory.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.inventory.domain.entity.InventoryItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface InventoryItemRepository extends BaseRepository<InventoryItem, UUID> {

    Optional<InventoryItem> findBySku(String sku);

    boolean existsBySku(String sku);

    @Query("SELECT i FROM InventoryItem i WHERE i.variant.id = :variantId AND i.deletedAt IS NULL")
    Optional<InventoryItem> findByVariantId(@Param("variantId") UUID variantId);
}