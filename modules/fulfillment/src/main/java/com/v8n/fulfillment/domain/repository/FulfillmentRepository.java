package com.v8n.fulfillment.domain.repository;

import com.v8n.fulfillment.domain.entity.Fulfillment;
import com.v8n.fulfillment.domain.entity.Fulfillment.FulfillmentStatus;
import com.v8n.modules.core.domain.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FulfillmentRepository extends BaseRepository<Fulfillment, UUID> {

    @Query("SELECT f FROM Fulfillment f WHERE f.order.id = :orderId AND f.deletedAt IS NULL")
    List<Fulfillment> findByOrderId(@Param("orderId") UUID orderId);

    @Query("SELECT f FROM Fulfillment f WHERE f.order.id = :orderId AND f.status = :status AND f.deletedAt IS NULL")
    Optional<Fulfillment> findByOrderIdAndStatus(@Param("orderId") UUID orderId, @Param("status") FulfillmentStatus status);

    @Query("SELECT f FROM Fulfillment f WHERE f.status = :status AND f.deletedAt IS NULL")
    List<Fulfillment> findByStatus(@Param("status") FulfillmentStatus status);

    @Query("SELECT COALESCE(MAX(f.displayId), 0) FROM Fulfillment f")
    Long findMaxDisplayId();
}