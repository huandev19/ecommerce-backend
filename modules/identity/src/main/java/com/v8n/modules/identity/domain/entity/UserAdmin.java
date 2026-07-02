package com.v8n.modules.identity.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "user_admins")
@Getter
@Setter
@NoArgsConstructor
public class UserAdmin {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @Column(name = "password_hash", columnDefinition = "TEXT")
    private String passwordHash;

    @Column(name = "activation_token")
    private UUID activationToken;

    @Column(name = "password_set_at")
    private LocalDateTime passwordSetAt;

    @Column(name = "avatar_url", length = 500)
    private String avatarUrl;

    @Column(length = 50)
    private String phone;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "failed_login_attempts", nullable = false)
    private Integer failedLoginAttempts = 0;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAdminRole> userAdminRoles = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserAdminPermission> userAdminPermissions = new HashSet<>();

    /**
     * Effective permissions = (Role permissions) UNION (User-level permissions).
     * Wildcard "*:*" (Super Admin) short-circuits to itself only.
     */
    public Set<String> getEffectivePermissions() {
        Set<String> perms = new HashSet<>();

        // Permissions from Roles
        if (userAdminRoles != null) {
            for (UserAdminRole uar : userAdminRoles) {
                Role role = uar.getRole();
                if (role != null && role.getRolePermissions() != null) {
                    for (RolePermission rp : role.getRolePermissions()) {
                        if (rp.getPermission() != null) {
                            perms.add(rp.getPermission().getCode());
                        }
                    }
                }
            }
        }

        // User-level override permissions (only add, never subtract)
        if (userAdminPermissions != null) {
            for (UserAdminPermission uap : userAdminPermissions) {
                if (uap.getPermission() != null) {
                    perms.add(uap.getPermission().getCode());
                }
            }
        }

        // Wildcard *:* short-circuits (Super Admin gets only *:*)
        if (perms.contains("*:*")) {
            Set<String> allPerms = new HashSet<>();
            allPerms.add("*:*");
            return allPerms;
        }

        return perms;
    }

    public boolean isActivated() {
        return passwordSetAt != null;
    }

    public boolean isLocked() {
        return lockedUntil != null && lockedUntil.isAfter(LocalDateTime.now());
    }

    public String getAvatarUrl() {

        return "http://localhost:9000";
    }

    public String getAvatarUrlS3() {
        String avatarUrl = this.getAvatarUrl();
        return avatarUrl + "/S3";
    }

}
