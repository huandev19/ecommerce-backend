package com.v8n.modules.identity.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminRoleId implements Serializable {
    @Column(name = "user_admin_id")
    private String userId;

    @Column(name = "role_id")
    private UUID roleId;
}
