package com.v8n.modules.order.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.order.domain.entity.Order;
import com.v8n.modules.order.domain.entity.OrderStatus;
import com.v8n.modules.order.domain.entity.PaymentStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends BaseRepository<Order, UUID> {

    Optional<Order> findByDisplayId(Long displayId);

    @Query("SELECT o FROM Order o WHERE o.customer.id = :customerId AND o.deletedAt IS NULL")
    List<Order> findByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT o FROM Order o WHERE o.email = :email AND o.deletedAt IS NULL")
    List<Order> findByEmail(@Param("email") String email);

    @Query("SELECT o FROM Order o WHERE o.status = :status AND o.deletedAt IS NULL")
    List<Order> findByStatus(@Param("status") OrderStatus status);

    @Query("SELECT o FROM Order o WHERE o.paymentStatus = :paymentStatus AND o.deletedAt IS NULL")
    List<Order> findByPaymentStatus(@Param("paymentStatus") PaymentStatus paymentStatus);

    @Query("SELECT COALESCE(MAX(o.displayId), 0) FROM Order o")
    Long findMaxDisplayId();
}