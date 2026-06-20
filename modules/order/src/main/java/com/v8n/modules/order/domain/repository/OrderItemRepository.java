package com.v8n.modules.order.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.order.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderItemRepository extends BaseRepository<OrderItem, UUID> {

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.id = :orderId AND oi.deletedAt IS NULL")
    List<OrderItem> findByOrderId(@Param("orderId") UUID orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.variant.id = :variantId AND oi.deletedAt IS NULL")
    List<OrderItem> findByVariantId(@Param("variantId") UUID variantId);
}