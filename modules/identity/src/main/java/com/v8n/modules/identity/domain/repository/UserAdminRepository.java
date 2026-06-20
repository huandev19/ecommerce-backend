package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.identity.domain.entity.UserAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAdminRepository extends JpaRepository<UserAdmin, String> {

    /**
     * Tìm user admin active theo email (soft unique constraint).
     */
    @Query("SELECT u FROM UserAdmin u WHERE u.email = :email AND u.isActive = true")
    Optional<UserAdmin> findByEmailActive(@Param("email") String email);

    /**
     * Tìm user admin theo email bất kể trạng thái active.
     */
    Optional<UserAdmin> findByEmail(String email);

    /**
     * Tìm user theo activation token.
     */
    Optional<UserAdmin> findByActivationToken(UUID activationToken);

    /**
     * Đếm số user đang active theo email (dùng để kiểm tra soft unique).
     */
    @Query("SELECT COUNT(u) FROM UserAdmin u WHERE u.email = :email AND u.isActive = true")
    long countByEmailActive(@Param("email") String email);

    /**
     * Tìm user active theo ID (không bị soft-delete).
     */
    @Query("SELECT u FROM UserAdmin u WHERE u.id = :id AND u.isActive = true")
    Optional<UserAdmin> findByIdActive(@Param("id") String id);
}
