package com.v8n.modules.identity.domain.entity;

import com.v8n.modules.core.domain.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User extends BaseEntity {

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "password_hash", columnDefinition = "TEXT")
    private String passwordHash;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @jakarta.persistence.Transient
    private String phone;

    @jakarta.persistence.Transient
    private String avatarUrl;

    @Enumerated(EnumType.STRING)
    @jakarta.persistence.Transient
    private UserStatus status = UserStatus.ACTIVE;

    @jakarta.persistence.Transient
    private boolean emailVerified = false;

    @jakarta.persistence.Transient
    private java.time.LocalDateTime lastLoginAt;

    @Column(name = "role", nullable = false, length = 50)
    private String role = "admin";

    @Column(name = "role_id")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private java.util.UUID roleId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", nullable = false, columnDefinition = "jsonb")

    private Map<String, Object> metadata;

    public enum UserStatus {
        ACTIVE, INACTIVE, BANNED
    }

    public String getFullName() {
        if (firstName == null && lastName == null) return null;
        String name = (firstName != null ? firstName : "").trim()
                + " "
                + (lastName != null ? lastName : "").trim();
        return name.trim().isEmpty() ? null : name.trim();
    }
}
