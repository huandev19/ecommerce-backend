package com.v8n.modules.identity.application.mapper;

import com.v8n.modules.identity.application.dto.AdminUserResponse;
import com.v8n.modules.identity.application.dto.RoleSummary;
import com.v8n.modules.identity.domain.entity.Role;
import com.v8n.modules.identity.domain.entity.UserAdmin;
import com.v8n.modules.identity.domain.entity.UserAdminPermission;
import com.v8n.modules.identity.domain.entity.UserAdminRole;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserAdminMapper {

    public AdminUserResponse toAdminUserResponse(UserAdmin user) {
        return AdminUserResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrlS3())
                .isActive(user.getIsActive() != null && user.getIsActive())
                .isActivated(user.isActivated())
                .roles(mapRoles(user.getUserAdminRoles()))
                .permissionsOverride(mapPermissionCodes(user.getUserAdminPermissions()))
                .effectivePermissions(user.getEffectivePermissions())
                .failedLoginAttempts(user.getFailedLoginAttempts())
                .lockedUntil(user.getLockedUntil())
                .lastLoginAt(user.getLastLoginAt())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public List<RoleSummary> mapRoles(Set<UserAdminRole> userAdminRoles) {
        if (userAdminRoles == null) return Collections.emptyList();
        return userAdminRoles.stream()
                .map(uar -> {
                    Role role = uar.getRole();
                    return RoleSummary.builder()
                            .desc(role.getDescription())
                            .name(role.getName())
                            .build();
                })
                .collect(Collectors.toList());
    }

    public Set<String> mapPermissionCodes(Set<UserAdminPermission> userAdminPermissions) {
        if (userAdminPermissions == null) return Collections.emptySet();
        return userAdminPermissions.stream()
                .map(uap -> uap.getPermission().getCode())
                .collect(Collectors.toSet());
    }

    public List<AdminUserResponse> toAdminUserResponseList(List<UserAdmin> users) {
        return users.stream().map(this::toAdminUserResponse).collect(Collectors.toList());
    }
}
