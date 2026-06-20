package com.v8n.modules.cart.domain.repository;

import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.core.domain.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends BaseRepository<Cart, UUID> {

    @Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId AND c.deletedAt IS NULL")
    List<Cart> findByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT c FROM Cart c WHERE c.email = :email AND c.deletedAt IS NULL")
    List<Cart> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Cart c WHERE c.customer.id = :customerId AND c.deletedAt IS NULL AND c.completedAt IS NULL ORDER BY c.createdAt DESC")
    Optional<Cart> findActiveCartByCustomerId(@Param("customerId") UUID customerId);

    @Query("SELECT c FROM Cart c WHERE c.deletedAt IS NULL AND c.completedAt IS NULL AND c.createdAt < :cutoffTime")
    List<Cart> findExpiredCarts(@Param("cutoffTime") LocalDateTime cutoffTime);
}
