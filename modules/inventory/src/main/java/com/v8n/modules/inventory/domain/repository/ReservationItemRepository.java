package com.v8n.modules.inventory.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.inventory.domain.entity.ReservationItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationItemRepository extends BaseRepository<ReservationItem, UUID> {

    List<ReservationItem> findByInventoryItemId(UUID inventoryItemId);

    List<ReservationItem> findByLineItemId(UUID lineItemId);

    List<ReservationItem> findByStatusAndExpiresAtBefore(String status, LocalDateTime dateTime);

    List<ReservationItem> findByStatus(String status);
}
