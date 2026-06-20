package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.identity.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(String name);

    boolean existsByName(String name);

    /**
     * Đếm số user admin đang được gán role này.
     */
    @Query("SELECT COUNT(ur) FROM UserAdminRole ur WHERE ur.role.id = :roleId")
    long countUsersByRoleId(@Param("roleId") UUID roleId);
}
