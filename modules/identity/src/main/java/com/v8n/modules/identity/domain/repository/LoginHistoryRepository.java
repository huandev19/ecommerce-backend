package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.identity.domain.entity.LoginHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String> {

    /**
     * Lọc login history theo email, status, khoảng thời gian.
     */
    @Query("SELECT lh FROM LoginHistory lh WHERE " +
           "(:email IS NULL OR lh.email LIKE %:email%) AND " +
           "(:status IS NULL OR lh.status = :status) AND " +
           "(:from IS NULL OR lh.attemptedAt >= :from) AND " +
           "(:to IS NULL OR lh.attemptedAt <= :to) " +
           "ORDER BY lh.attemptedAt DESC")
    Page<LoginHistory> findFiltered(
            @Param("email") String email,
            @Param("status") com.v8n.modules.identity.domain.enums.LoginStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    /**
     * Lấy toàn bộ records cho export (không phân trang).
     */
    @Query("SELECT lh FROM LoginHistory lh WHERE " +
           "(:email IS NULL OR lh.email LIKE %:email%) AND " +
           "(:status IS NULL OR lh.status = :status) AND " +
           "(:from IS NULL OR lh.attemptedAt >= :from) AND " +
           "(:to IS NULL OR lh.attemptedAt <= :to) " +
           "ORDER BY lh.attemptedAt DESC")
    List<LoginHistory> findFilteredAll(
            @Param("email") String email,
            @Param("status") com.v8n.modules.identity.domain.enums.LoginStatus status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);
}
