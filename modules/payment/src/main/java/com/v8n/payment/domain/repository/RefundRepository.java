package com.v8n.payment.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.payment.domain.entity.Refund;
import com.v8n.payment.domain.entity.Refund.RefundStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RefundRepository extends BaseRepository<Refund, UUID> {

    @Query("SELECT r FROM Refund r WHERE r.paymentCollection.id = :collectionId AND r.deletedAt IS NULL")
    List<Refund> findByPaymentCollectionId(@Param("collectionId") UUID collectionId);

    @Query("SELECT r FROM Refund r WHERE r.status = :status AND r.deletedAt IS NULL")
    List<Refund> findByStatus(@Param("status") RefundStatus status);

    @Query("SELECT r FROM Refund r WHERE r.providerRefundId = :providerRefundId AND r.deletedAt IS NULL")
    List<Refund> findByProviderRefundId(@Param("providerRefundId") String providerRefundId);
}