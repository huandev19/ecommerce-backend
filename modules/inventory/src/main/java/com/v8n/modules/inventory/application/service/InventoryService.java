package com.v8n.modules.inventory.application.service;

import com.v8n.modules.inventory.application.dto.InventoryItemRequest;
import com.v8n.modules.inventory.application.dto.InventoryItemResponse;
import com.v8n.modules.inventory.application.dto.ReservationRequest;
import com.v8n.modules.inventory.application.dto.ReservationResponse;
import com.v8n.modules.inventory.domain.entity.InventoryItem;
import com.v8n.modules.inventory.domain.entity.InventoryLevel;
import com.v8n.modules.inventory.domain.entity.ReservationItem;
import com.v8n.modules.inventory.domain.repository.InventoryItemRepository;
import com.v8n.modules.inventory.domain.repository.InventoryLevelRepository;
import com.v8n.modules.inventory.domain.repository.ReservationItemRepository;
import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.catalog.domain.repository.ProductVariantRepository;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryLevelRepository inventoryLevelRepository;
    private final ReservationItemRepository reservationItemRepository;
    private final ProductVariantRepository variantRepository;

    private static final int DEFAULT_RESERVATION_MINUTES = 30;

    // ===== Inventory Item Methods =====

    @Transactional(readOnly = true)
    public InventoryItemResponse getInventoryItemById(UUID itemId) {
        InventoryItem item = findInventoryItemById(itemId);
        return toInventoryItemResponse(item);
    }

    @Transactional(readOnly = true)
    public InventoryItemResponse getInventoryItemBySku(String sku) {
        InventoryItem item = inventoryItemRepository.findBySku(sku)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));
        return toInventoryItemResponse(item);
    }

    @Transactional(readOnly = true)
    public InventoryItemResponse getInventoryItemByVariantId(UUID variantId) {
        InventoryItem item = inventoryItemRepository.findByVariantId(variantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));
        return toInventoryItemResponse(item);
    }

    @Transactional(readOnly = true)
    public List<InventoryItemResponse> getAllInventoryItems() {
        return inventoryItemRepository.findAll().stream()
                .map(this::toInventoryItemResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InventoryItemResponse createInventoryItem(InventoryItemRequest request) {
        InventoryItem item = new InventoryItem();
        item.setSku(request.getSku());
        item.setTitle(request.getTitle());
        item.setRequiresShipping(request.isRequiresShipping());
        item.setLocation(request.getLocation());
        item.setOverselling(request.isOverselling());
        item.setRestockThreshold(request.getRestockThreshold());

        if (request.getVariantId() != null) {
            ProductVariant variant = variantRepository.findByIdNotDeleted(request.getVariantId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.VARIANT_NOT_FOUND));
            item.setVariant(variant);
        }

        item = inventoryItemRepository.save(item);
        log.info("Created inventory item: {}", item.getId());
        return toInventoryItemResponse(item);
    }

    @Transactional
    public InventoryItemResponse updateInventoryItem(UUID itemId, InventoryItemRequest request) {
        InventoryItem item = findInventoryItemById(itemId);

        if (request.getSku() != null) item.setSku(request.getSku());
        if (request.getTitle() != null) item.setTitle(request.getTitle());
        if (request.getLocation() != null) item.setLocation(request.getLocation());
        item.setRequiresShipping(request.isRequiresShipping());
        item.setOverselling(request.isOverselling());
        if (request.getRestockThreshold() != null) item.setRestockThreshold(request.getRestockThreshold());

        item = inventoryItemRepository.save(item);
        log.info("Updated inventory item: {}", item.getId());
        return toInventoryItemResponse(item);
    }

    @Transactional
    public void adjustQuantity(UUID itemId, int delta, String reason) {
        InventoryItem item = findInventoryItemById(itemId);
        item.setQuantity(item.getQuantity() + delta);
        item = inventoryItemRepository.save(item);
        log.info("Adjusted inventory item {} quantity by {} ({})", itemId, delta, reason);
    }

    // ===== Reservation Methods =====

    @Transactional
    public ReservationResponse reserveStock(ReservationRequest request) {
        InventoryItem item = findInventoryItemById(request.getInventoryItemId());

        int availableQuantity = item.getAvailableQuantity();
        if (!item.isOverselling() && availableQuantity < request.getQuantity()) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK,
                    "Requested: " + request.getQuantity() + ", Available: " + availableQuantity);
        }

        ReservationItem reservation = new ReservationItem();
        reservation.setInventoryItem(item);
        reservation.setLineItemId(request.getLineItemId());
        reservation.setQuantity(request.getQuantity());
        reservation.setExpiresAt(LocalDateTime.now().plusMinutes(DEFAULT_RESERVATION_MINUTES));
        reservation.setStatus("RESERVED");
        reservation.setActive(true);

        item.setReservedQuantity(item.getReservedQuantity() + request.getQuantity());
        inventoryItemRepository.save(item);

        reservation = reservationItemRepository.save(reservation);
        log.info("Reserved {} units for line item {}", request.getQuantity(), request.getLineItemId());
        return toReservationResponse(reservation);
    }

    @Transactional
    public void releaseReservation(UUID reservationId) {
        ReservationItem reservation = findReservationById(reservationId);

        if (!reservation.isActive()) {
            log.warn("Reservation {} already released", reservationId);
            return;
        }

        InventoryItem item = reservation.getInventoryItem();
        item.setReservedQuantity(item.getReservedQuantity() - reservation.getQuantity());
        inventoryItemRepository.save(item);

        reservation.setActive(false);
        reservationItemRepository.save(reservation);
        log.info("Released reservation {}", reservationId);
    }

    @Transactional
    public void commitReservation(UUID reservationId) {
        ReservationItem reservation = findReservationById(reservationId);

        if (!reservation.isActive()) {
            throw new BusinessException(ErrorCode.RESERVATION_NOT_FOUND, "Reservation already released");
        }

        InventoryItem item = reservation.getInventoryItem();
        item.setQuantity(item.getQuantity() - reservation.getQuantity());
        item.setReservedQuantity(item.getReservedQuantity() - reservation.getQuantity());
        inventoryItemRepository.save(item);

        reservation.setActive(false);
        reservation.setStatus("COMMITTED");
        reservationItemRepository.save(reservation);
        log.info("Committed reservation {}", reservationId);
    }

    @Transactional
    public void cancelReservation(UUID reservationId) {
        ReservationItem reservation = findReservationById(reservationId);

        if (!reservation.isActive()) {
            log.warn("Reservation {} already cancelled", reservationId);
            return;
        }

        InventoryItem item = reservation.getInventoryItem();
        item.setReservedQuantity(item.getReservedQuantity() - reservation.getQuantity());
        inventoryItemRepository.save(item);

        reservation.setActive(false);
        reservation.setStatus("CANCELLED");
        reservationItemRepository.save(reservation);
        log.info("Cancelled reservation {}", reservationId);
    }

    @Transactional
    public void expireReservations() {
        List<ReservationItem> expired = reservationItemRepository.findByStatusAndExpiresAtBefore(
                "RESERVED", LocalDateTime.now());

        for (ReservationItem reservation : expired) {
            if (reservation.isActive()) {
                InventoryItem item = reservation.getInventoryItem();
                item.setReservedQuantity(item.getReservedQuantity() - reservation.getQuantity());
                inventoryItemRepository.save(item);

                reservation.setActive(false);
                reservation.setStatus("EXPIRED");
                reservationItemRepository.save(reservation);
            }
        }
        log.info("Expired {} reservations", expired.size());
    }

    // ===== Convenience Methods for Order Integration =====

    @Transactional(readOnly = true)
    public boolean checkAvailability(UUID variantId, int quantity) {
        InventoryItem item = inventoryItemRepository.findByVariantId(variantId).orElse(null);
        if (item == null) {
            return false;
        }
        if (item.isOverselling()) {
            return true;
        }
        return item.getAvailableQuantity() >= quantity;
    }

    @Transactional
    public void reserveInventory(UUID variantId, int quantity) {
        InventoryItem item = inventoryItemRepository.findByVariantId(variantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

        if (!item.isOverselling() && item.getAvailableQuantity() < quantity) {
            throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK,
                    "Requested: " + quantity + ", Available: " + item.getAvailableQuantity());
        }

        item.setReservedQuantity(item.getReservedQuantity() + quantity);
        inventoryItemRepository.save(item);
        log.info("Reserved {} units for variant {}", quantity, variantId);
    }

    @Transactional
    public void releaseReservation(UUID variantId, int quantity) {
        InventoryItem item = inventoryItemRepository.findByVariantId(variantId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));

        int toRelease = Math.min(quantity, item.getReservedQuantity());
        item.setReservedQuantity(item.getReservedQuantity() - toRelease);
        inventoryItemRepository.save(item);
        log.info("Released {} units reservation for variant {}", toRelease, variantId);
    }

    // ===== Private Helper Methods =====

    private InventoryItem findInventoryItemById(UUID itemId) {
        return inventoryItemRepository.findByIdNotDeleted(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVENTORY_ITEM_NOT_FOUND));
    }

    private ReservationItem findReservationById(UUID reservationId) {
        return reservationItemRepository.findByIdNotDeleted(reservationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESERVATION_NOT_FOUND));
    }

    private InventoryItemResponse toInventoryItemResponse(InventoryItem item) {
        return InventoryItemResponse.builder()
                .id(item.getId())
                .variantId(item.getVariant() != null ? item.getVariant().getId() : null)
                .sku(item.getSku())
                .title(item.getTitle())
                .quantity(item.getQuantity())
                .reservedQuantity(item.getReservedQuantity())
                .availableQuantity(item.getAvailableQuantity())
                .incomingQuantity(item.getIncomingQuantity())
                .location(item.getLocation())
                .requiresShipping(item.isRequiresShipping())
                .overselling(item.isOverselling())
                .restockThreshold(item.getRestockThreshold())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private ReservationResponse toReservationResponse(ReservationItem reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .inventoryItemId(reservation.getInventoryItem().getId())
                .lineItemId(reservation.getLineItemId())
                .quantity(reservation.getQuantity())
                .status(reservation.getStatus())
                .expiresAt(reservation.getExpiresAt())
                .isActive(reservation.isActive())
                .createdAt(reservation.getCreatedAt())
                .build();
    }
}