package com.v8n.promotion.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.promotion.domain.entity.Discount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository extends BaseRepository<Discount, UUID> {

    Optional<Discount> findByCode(String code);

    @Query("SELECT d FROM Discount d WHERE d.code = :code AND d.deletedAt IS NULL")
    Optional<Discount> findByCodeNotDeleted(@Param("code") String code);

    @Query("SELECT d FROM Discount d WHERE d.isActive = true AND d.deletedAt IS NULL")
    List<Discount> findAllActive();

    @Query("SELECT d FROM Discount d WHERE d.isActive = true AND d.startsAt <= :now AND (d.endsAt IS NULL OR d.endsAt >= :now) AND d.deletedAt IS NULL")
    List<Discount> findAllValid(@Param("now") LocalDateTime now);

    @Query("SELECT d FROM Discount d WHERE d.code = :code AND d.isActive = true AND d.startsAt <= :now AND (d.endsAt IS NULL OR d.endsAt >= :now) AND d.deletedAt IS NULL")
    Optional<Discount> findValidByCode(@Param("code") String code, @Param("now") LocalDateTime now);
}