package com.v8n.modules.cart.domain.repository;

import com.v8n.modules.cart.domain.entity.LineItem;
import com.v8n.modules.core.domain.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface LineItemRepository extends BaseRepository<LineItem, UUID> {

    @Query("SELECT li FROM LineItem li WHERE li.cart.id = :cartId AND li.deletedAt IS NULL")
    List<LineItem> findByCartId(@Param("cartId") UUID cartId);

    @Query("SELECT li FROM LineItem li WHERE li.cart.id = :cartId AND li.variant.id = :variantId AND li.deletedAt IS NULL")
    List<LineItem> findByCartIdAndVariantId(@Param("cartId") UUID cartId, @Param("variantId") UUID variantId);
}