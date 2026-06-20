package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class AdminUserResponse {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private String avatarUrl;
    private boolean isActive;
    private boolean isActivated;
    private List<RoleSummary> roles;
    private Set<String> permissionsOverride;
    private Set<String> effectivePermissions;
    private Integer failedLoginAttempts;
    private LocalDateTime lockedUntil;
    private LocalDateTime lastLoginAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
