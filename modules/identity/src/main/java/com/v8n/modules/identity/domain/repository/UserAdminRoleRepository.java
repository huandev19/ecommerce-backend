package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.identity.domain.entity.UserAdminRole;
import com.v8n.modules.identity.domain.entity.UserAdminRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdminRoleRepository extends JpaRepository<UserAdminRole, UserAdminRoleId> {

    List<UserAdminRole> findByUserId(String userId);

    @Modifying
    @Query("DELETE FROM UserAdminRole ur WHERE ur.user.id = :userId")
    void deleteByUserId(@Param("userId") String userId);
}
