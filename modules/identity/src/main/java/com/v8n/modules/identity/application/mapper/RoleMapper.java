package com.v8n.modules.identity.application.mapper;

import com.v8n.modules.identity.application.dto.RoleDetailResponse;
import com.v8n.modules.identity.application.dto.RoleResponse;
import com.v8n.modules.identity.domain.entity.Role;
import com.v8n.modules.identity.domain.entity.RolePermission;
import com.v8n.modules.identity.domain.entity.UserAdminRole;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleResponse toRoleResponse(Role role) {
        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(mapPermissionCodes(role.getRolePermissions()))
                .permissionsCount(role.getRolePermissions() != null ? role.getRolePermissions().size() : 0)
                .usersCount(0)
                .isSystem(role.getIsSystem() != null && role.getIsSystem())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }

    public RoleDetailResponse toRoleDetailResponse(Role role) {
        return RoleDetailResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .permissions(mapPermissionCodes(role.getRolePermissions()))
                .users(mapUsers(role.getUserAdminRoles()))
                .isSystem(role.getIsSystem() != null && role.getIsSystem())
                .createdAt(role.getCreatedAt())
                .updatedAt(role.getUpdatedAt())
                .build();
    }

    public Set<String> mapPermissionCodes(Set<RolePermission> rolePermissions) {
        if (rolePermissions == null) return Collections.emptySet();
        return rolePermissions.stream()
                .map(rp -> rp.getPermission().getCode())
                .collect(Collectors.toSet());
    }

    public List<RoleDetailResponse.UserSummary> mapUsers(Set<UserAdminRole> userAdminRoles) {
        if (userAdminRoles == null) return Collections.emptyList();
        return userAdminRoles.stream()
                .map(uar -> RoleDetailResponse.UserSummary.builder()
                        .id(uar.getUser().getId())
                        .email(uar.getUser().getEmail())
                        .firstName(uar.getUser().getFirstName())
                        .lastName(uar.getUser().getLastName())
                        .build())
                .collect(Collectors.toList());
    }

    public List<RoleResponse> toRoleResponseList(List<Role> roles) {
        return roles.stream().map(this::toRoleResponse).collect(Collectors.toList());
    }
}
