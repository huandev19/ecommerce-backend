package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.CreateRoleRequest;
import com.v8n.modules.identity.application.dto.RoleDetailResponse;
import com.v8n.modules.identity.application.dto.RoleResponse;
import com.v8n.modules.identity.application.dto.UpdateRoleRequest;
import com.v8n.modules.identity.application.mapper.RoleMapper;
import com.v8n.modules.identity.domain.entity.Permission;
import com.v8n.modules.identity.domain.entity.Role;
import com.v8n.modules.identity.domain.entity.RolePermission;
import com.v8n.modules.identity.domain.entity.RolePermissionId;
import com.v8n.modules.identity.domain.repository.PermissionRepository;
import com.v8n.modules.identity.domain.repository.RolePermissionRepository;
import com.v8n.modules.identity.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final RoleMapper roleMapper;

    @Transactional(readOnly = true)
    public List<RoleResponse> listRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleResponse> responses = roleMapper.toRoleResponseList(roles);
        // Populate usersCount for each role
        for (int i = 0; i < roles.size(); i++) {
            long count = roleRepository.countUsersByRoleId(roles.get(i).getId());
            responses.get(i).setUsersCount(count);
            responses.get(i).setPermissionsCount(
                    roles.get(i).getRolePermissions() != null ? roles.get(i).getRolePermissions().size() : 0
            );
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public RoleDetailResponse getRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
        return roleMapper.toRoleDetailResponse(role);
    }

    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        // Check unique name
        if (roleRepository.existsByName(request.getName())) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Tên role đã tồn tại");
        }

        // Validate permissions
        validatePermissions(request.getPermissions());

        Role role = new Role();
        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setIsSystem(false);
        role.setCreatedAt(LocalDateTime.now());
        role = roleRepository.save(role);

        // Assign permissions (hard sync for new role)
        syncRolePermissions(role, request.getPermissions());

        RoleResponse response = roleMapper.toRoleResponse(role);
        response.setPermissions(request.getPermissions());
        response.setPermissionsCount(request.getPermissions().size());
        response.setUsersCount(0);
        return response;
    }

    @Transactional
    public RoleResponse updateRole(UUID roleId, UpdateRoleRequest request) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

        // Cannot modify system roles
        if (role.getIsSystem() != null && role.getIsSystem()) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể sửa role hệ thống");
        }

        // Check unique name (exclude current role)
        roleRepository.findByName(request.getName()).ifPresent(existing -> {
            if (!existing.getId().equals(roleId)) {
                throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Tên role đã tồn tại");
            }
        });

        // Validate permissions
        validatePermissions(request.getPermissions());

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setUpdatedAt(LocalDateTime.now());

        // Hard sync permissions
        syncRolePermissions(role, request.getPermissions());

        roleRepository.save(role);

        RoleResponse response = roleMapper.toRoleResponse(role);
        response.setPermissions(request.getPermissions());
        response.setPermissionsCount(request.getPermissions().size());
        response.setUsersCount(roleRepository.countUsersByRoleId(roleId));
        return response;
    }

    @Transactional
    public void deleteRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));

        // Cannot delete system roles
        if (role.getIsSystem() != null && role.getIsSystem()) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể xóa role hệ thống");
        }

        // Check if role has users
        long userCount = roleRepository.countUsersByRoleId(roleId);
        if (userCount > 0) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Role đang được gán cho " + userCount + " user. Không thể xóa.");
        }

        rolePermissionRepository.deleteByRoleId(roleId);
        roleRepository.delete(role);
    }

    /**
     * Validate that all permission codes exist in the database.
     */
    private void validatePermissions(Set<String> permissionCodes) {
        if (permissionCodes == null || permissionCodes.isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Phải chọn ít nhất 1 quyền");
        }

        for (String code : permissionCodes) {
            permissionRepository.findByCode(code)
                    .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST,
                            "Permission không hợp lệ: " + code));
        }
    }

    /**
     * Hard sync: delete all existing role_permission, insert new set.
     */
    private void syncRolePermissions(Role role, Set<String> permissionCodes) {
        // Delete existing
        rolePermissionRepository.deleteByRoleId(role.getId());
        rolePermissionRepository.flush();

        // Insert new
        Set<RolePermission> newPermissions = new HashSet<>();
        for (String code : permissionCodes) {
            Permission perm = permissionRepository.findByCode(code).orElseThrow();
            RolePermission rp = new RolePermission();
            rp.setId(new RolePermissionId(role.getId(), perm.getId()));
            rp.setRole(role);
            rp.setPermission(perm);
            rp.setCreatedAt(LocalDateTime.now());
            newPermissions.add(rp);
        }
        rolePermissionRepository.saveAll(newPermissions);
    }
}
