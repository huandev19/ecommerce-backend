package com.v8n.payment.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.payment.domain.entity.PaymentCollection;
import com.v8n.payment.domain.entity.PaymentCollection.PaymentCollectionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentCollectionRepository extends BaseRepository<PaymentCollection, UUID> {

    @Query("SELECT pc FROM PaymentCollection pc WHERE pc.order.id = :orderId AND pc.deletedAt IS NULL")
    Optional<PaymentCollection> findByOrderId(@Param("orderId") UUID orderId);

    @Query("SELECT pc FROM PaymentCollection pc WHERE pc.status = :status AND pc.deletedAt IS NULL")
    List<PaymentCollection> findByStatus(@Param("status") PaymentCollectionStatus status);

    @Query("SELECT pc FROM PaymentCollection pc WHERE pc.order.id = :orderId AND pc.status = :status AND pc.deletedAt IS NULL")
    Optional<PaymentCollection> findByOrderIdAndStatus(@Param("orderId") UUID orderId, @Param("status") PaymentCollectionStatus status);
}