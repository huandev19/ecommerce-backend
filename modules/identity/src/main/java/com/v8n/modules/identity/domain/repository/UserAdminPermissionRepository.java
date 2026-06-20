package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.identity.domain.entity.UserAdminPermission;
import com.v8n.modules.identity.domain.entity.UserAdminPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdminPermissionRepository extends JpaRepository<UserAdminPermission, UserAdminPermissionId> {

    List<UserAdminPermission> findByUserId(String userId);

    @Modifying
    @Query("DELETE FROM UserAdminPermission up WHERE up.user.id = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
