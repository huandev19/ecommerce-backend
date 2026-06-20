package com.v8n.modules.inventory.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.inventory.domain.entity.InventoryLevel;

import java.util.List;
import java.util.UUID;

public interface InventoryLevelRepository extends BaseRepository<InventoryLevel, UUID> {

    List<InventoryLevel> findByInventoryItemId(UUID inventoryItemId);

    List<InventoryLevel> findByLocationId(UUID locationId);

    List<InventoryLevel> findByInventoryItemIdAndLocationId(UUID inventoryItemId, UUID locationId);
}
