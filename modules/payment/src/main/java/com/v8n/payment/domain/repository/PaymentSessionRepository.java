package com.v8n.payment.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.payment.domain.entity.PaymentSession;
import com.v8n.payment.domain.entity.PaymentSession.PaymentSessionStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentSessionRepository extends BaseRepository<PaymentSession, UUID> {

    @Query("SELECT ps FROM PaymentSession ps WHERE ps.paymentCollection.id = :collectionId AND ps.deletedAt IS NULL")
    List<PaymentSession> findByPaymentCollectionId(@Param("collectionId") UUID collectionId);

    @Query("SELECT ps FROM PaymentSession ps WHERE ps.status = :status AND ps.deletedAt IS NULL")
    List<PaymentSession> findByStatus(@Param("status") PaymentSessionStatus status);

    @Query("SELECT ps FROM PaymentSession ps WHERE ps.status IN :statuses AND ps.expiresAt < :now AND ps.deletedAt IS NULL")
    List<PaymentSession> findByStatusInAndExpiresAtBefore(@Param("statuses") List<PaymentSessionStatus> statuses, @Param("now") LocalDateTime now);

    @Query("SELECT ps FROM PaymentSession ps WHERE ps.providerSessionId = :providerSessionId AND ps.deletedAt IS NULL")
    Optional<PaymentSession> findByProviderSessionId(@Param("providerSessionId") String providerSessionId);
}