package com.v8n.payment.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.payment.domain.entity.Payment;
import com.v8n.payment.domain.entity.Payment.PaymentStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends BaseRepository<Payment, UUID> {

    @Query("SELECT p FROM Payment p WHERE p.paymentCollection.id = :collectionId AND p.deletedAt IS NULL")
    List<Payment> findByPaymentCollectionId(@Param("collectionId") UUID collectionId);

    @Query("SELECT p FROM Payment p WHERE p.status = :status AND p.deletedAt IS NULL")
    List<Payment> findByStatus(@Param("status") PaymentStatus status);

    @Query("SELECT p FROM Payment p WHERE p.providerTransactionId = :transactionId AND p.deletedAt IS NULL")
    Optional<Payment> findByProviderTransactionId(@Param("transactionId") String providerTransactionId);
}