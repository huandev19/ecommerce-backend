package com.v8n.modules.identity.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * JPA Entity mapping to the 'role' table.
 * Replaces the old {@code Role} enum which served customer-facing roles.
 * The old enum may be renamed to {@code CustomerRole} or removed if unused.
 */
@Entity
@Table(name = "role")
@Getter
@Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(length = 500)
    private String description;

    @Column(name = "is_system", nullable = false)
    private Boolean isSystem = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RolePermission> rolePermissions = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserAdminRole> userAdminRoles = new HashSet<>();
}
