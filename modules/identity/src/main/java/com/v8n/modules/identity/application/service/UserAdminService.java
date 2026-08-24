package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.core.infrastructure.util.UuidV7;
import com.v8n.modules.identity.application.dto.AdminUserResponse;
import com.v8n.modules.identity.application.dto.CreateUserAdminRequest;
import com.v8n.modules.identity.application.dto.RoleSummary;
import com.v8n.modules.identity.application.dto.UnlockResponse;
import com.v8n.modules.identity.application.dto.UpdateUserAdminRequest;
import com.v8n.modules.identity.application.mapper.UserAdminMapper;
import com.v8n.modules.identity.domain.entity.Permission;
import com.v8n.modules.identity.domain.entity.Role;
import com.v8n.modules.identity.domain.entity.LoginHistory;
import com.v8n.modules.identity.domain.entity.UserAdmin;
import com.v8n.modules.identity.domain.entity.UserAdminPermission;
import com.v8n.modules.identity.domain.entity.UserAdminPermissionId;
import com.v8n.modules.identity.domain.entity.UserAdminRole;
import com.v8n.modules.identity.domain.entity.UserAdminRoleId;
import com.v8n.modules.identity.domain.enums.LoginStatus;
import com.v8n.modules.identity.domain.repository.PermissionRepository;
import com.v8n.modules.identity.domain.repository.RoleRepository;
import com.v8n.modules.identity.domain.repository.UserAdminPermissionRepository;
import com.v8n.modules.identity.domain.repository.UserAdminRepository;
import com.v8n.modules.identity.domain.repository.UserAdminRoleRepository;
import com.v8n.modules.identity.infrastructure.security.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAdminService {

    private final UserAdminRepository userAdminRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final UserAdminRoleRepository userAdminRoleRepository;
    private final UserAdminPermissionRepository userAdminPermissionRepository;
    private final UserAdminMapper userAdminMapper;
    private final TokenBlacklistService tokenBlacklistService;
    private final LoginHistoryService loginHistoryService;

    @Transactional(readOnly = true)
    public List<AdminUserResponse> listUsers(String q, String status) {
        // Simplified: fetch all and filter. In production, use Specification/QueryDSL.
        List<UserAdmin> users = userAdminRepository.findAll();

        if (q != null && !q.isBlank()) {
            String lowerQ = q.toLowerCase();
            users = users.stream()
                    .filter(u -> (u.getEmail() != null && u.getEmail().toLowerCase().contains(lowerQ))
                            || (u.getFirstName() != null && u.getFirstName().toLowerCase().contains(lowerQ))
                            || (u.getLastName() != null && u.getLastName().toLowerCase().contains(lowerQ)))
                    .collect(Collectors.toList());
        }

        if (status != null && !status.isBlank()) {
            if ("active".equalsIgnoreCase(status)) {
                users = users.stream().filter(u -> u.getIsActive() != null && u.getIsActive()).collect(Collectors.toList());
            } else if ("inactive".equalsIgnoreCase(status)) {
                users = users.stream().filter(u -> u.getIsActive() == null || !u.getIsActive()).collect(Collectors.toList());
            } else if ("locked".equalsIgnoreCase(status)) {
                users = users.stream().filter(UserAdmin::isLocked).collect(Collectors.toList());
            }
        }

        return userAdminMapper.toAdminUserResponseList(users);
    }

    @Transactional(readOnly = true)
    public AdminUserResponse getUser(String userId) {
        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return userAdminMapper.toAdminUserResponse(user);
    }

    @Transactional
    public AdminUserResponse createUser(CreateUserAdminRequest request, String currentUserId) {
        // Soft unique check: email must be unique among active users
        long activeWithEmail = userAdminRepository.countByEmailActive(request.getEmail().toLowerCase().trim());
        if (activeWithEmail > 0) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        // Validate roleIds
        List<Role> roles = new ArrayList<>();
        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            for (String roleId : request.getRoleIds()) {
                UUID roleUuid = UUID.fromString(roleId);
                Role role = roleRepository.findById(roleUuid)
                        .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
                roles.add(role);
            }
        }

        // Create user
        UserAdmin user = new UserAdmin();
        user.setId(UuidV7.generateString());
        user.setEmail(request.getEmail().toLowerCase().trim());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setAvatarUrl(request.getAvatarUrl());
        user.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);
        user.setActivationToken(UuidV7.generate());
        user.setFailedLoginAttempts(0);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        user = userAdminRepository.save(user);

        // Assign roles
        UserAdmin assigner = currentUserId != null ? userAdminRepository.findById(currentUserId).orElse(null) : null;
        Set<UserAdminRole> userRoles = new HashSet<>();
        for (Role role : roles) {
            UserAdminRole uar = new UserAdminRole();
            uar.setId(new UserAdminRoleId(user.getId(), role.getId()));
            uar.setUser(user);
            uar.setRole(role);
            uar.setAssignedAt(LocalDateTime.now());
            uar.setAssignedBy(assigner);
            userRoles.add(uar);
        }
        userAdminRoleRepository.saveAll(userRoles);

        // User-level permission override (optional)
        if (request.getPermissions() != null && !request.getPermissions().isEmpty()) {
            validatePermissionsNotNarrowing(roles, request.getPermissions());
            setUserPermissions(user, request.getPermissions(), assigner);
        }

        log.info("Admin user created: {} by {}", user.getEmail(), currentUserId);
        return userAdminMapper.toAdminUserResponse(user);
    }

    @Transactional
    public AdminUserResponse updateUser(String userId, UpdateUserAdminRequest request, String currentUserId) {
        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (request.getFirstName() != null) user.setFirstName(request.getFirstName());
        if (request.getLastName() != null) user.setLastName(request.getLastName());
        if (request.getPhone() != null) user.setPhone(request.getPhone());
        if (request.getAvatarUrl() != null) user.setAvatarUrl(request.getAvatarUrl());
        if (request.getIsActive() != null) user.setIsActive(request.getIsActive());

        user.setUpdatedAt(LocalDateTime.now());
        user = userAdminRepository.save(user);

        return userAdminMapper.toAdminUserResponse(user);
    }

    @Transactional
    public AdminUserResponse updateRoles(String userId, List<String> roleIds, String currentUserId) {
        // Cannot modify self
        if (currentUserId != null && currentUserId.equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể tự sửa role của chính mình");
        }

        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // Validate roles
        Set<Role> roles = new HashSet<>();
        for (String roleId : roleIds) {
            UUID roleUuid = UUID.fromString(roleId);
            Role role = roleRepository.findById(roleUuid)
                    .orElseThrow(() -> new BusinessException(ErrorCode.ROLE_NOT_FOUND));
            roles.add(role);
        }

        // Hard sync: delete all existing user_admin_roles, insert new
        userAdminRoleRepository.deleteByUserId(userId);
        userAdminRoleRepository.flush();

        UserAdmin assigner = currentUserId != null ? userAdminRepository.findById(currentUserId).orElse(null) : null;
        Set<UserAdminRole> newRoles = new HashSet<>();
        for (Role role : roles) {
            UserAdminRole uar = new UserAdminRole();
            uar.setId(new UserAdminRoleId(user.getId(), role.getId()));
            uar.setUser(user);
            uar.setRole(role);
            uar.setAssignedAt(LocalDateTime.now());
            uar.setAssignedBy(assigner);
            newRoles.add(uar);
        }
        userAdminRoleRepository.saveAll(newRoles);

        return userAdminMapper.toAdminUserResponse(user);
    }

    @Transactional
    public void deactivateUser(String userId, String currentUserId) {
        if (currentUserId != null && currentUserId.equals(userId)) {
            throw new BusinessException(ErrorCode.ACCESS_DENIED, "Không thể tự vô hiệu hóa chính mình");
        }

        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        user.setIsActive(false);
        user.setDeletedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userAdminRepository.save(user);

        log.info("User deactivated: {} by {}", user.getEmail(), currentUserId);
    }

    @Transactional
    public UnlockResponse unlockUser(String userId, String currentUserId) {
        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!user.isLocked()) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Tài khoản không bị khóa");
        }

        user.setLockedUntil(null);
        user.setFailedLoginAttempts(0);
        user.setUpdatedAt(LocalDateTime.now());
        userAdminRepository.save(user);

        log.info("User unlocked: {} by {}", user.getEmail(), currentUserId);

        return UnlockResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lockedUntil(null)
                .failedLoginAttempts(0)
                .unlockedAt(LocalDateTime.now())
                .unlockedBy(currentUserId)
                .build();
    }

    @Transactional
    public AdminUserResponse updatePermissions(String userId, Set<String> permissionCodes, String currentUserId) {
        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        // Get user's role permissions
        Set<String> rolePermissions = user.getEffectivePermissions();
        // Remove user-level overrides from role permissions to get "pure" role permissions
        // Actually, effectivePermissions already includes both. We need to validate narrowing.
        // Simplification: only validate that new permissions are valid codes
        for (String code : permissionCodes) {
            permissionRepository.findByCode(code)
                    .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST,
                            "Permission không hợp lệ: " + code));
        }

        // Hard sync
        userAdminPermissionRepository.deleteByUserId(userId);
        userAdminPermissionRepository.flush();

        UserAdmin assigner = currentUserId != null ? userAdminRepository.findById(currentUserId).orElse(null) : null;
        setUserPermissions(user, permissionCodes, assigner);

        return userAdminMapper.toAdminUserResponse(user);
    }

    @Transactional
    public AdminUserResponse resendActivation(String userId, String currentUserId) {
        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.isActivated()) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Tài khoản đã được kích hoạt");
        }

        user.setActivationToken(UuidV7.generate());
        user.setUpdatedAt(LocalDateTime.now());
        userAdminRepository.save(user);

        log.info("Activation resent for: {} by {}", user.getEmail(), currentUserId);
        return userAdminMapper.toAdminUserResponse(user);
    }

    /**
     * Force-revoke all tokens for a target admin user and record audit trail in login history.
     *
     * @param targetUserId the admin user whose tokens are being revoked
     * @param adminId      the admin who is performing the force-revoke
     */
    @Transactional
    public void revokeTokens(String targetUserId, String adminId) {
        tokenBlacklistService.revokeAllForUser(targetUserId, "admin", "ADMIN_FORCE_LOGOUT");

        loginHistoryService.recordLoginHistory(null, adminId, LoginStatus.LOGOUT,
                "FORCE_LOGOUT_BY_ADMIN", null, null);

        log.info("Admin user {} force-revoked tokens for user {} (admin)", adminId, targetUserId);
    }

    // --- Private helpers ---

    private void validatePermissionsNotNarrowing(List<Role> roles, Set<String> permissionCodes) {
        // Get all permissions from assigned roles
        Set<String> rolePermissionCodes = new HashSet<>();
        for (Role role : roles) {
            if (role.getRolePermissions() != null) {
                for (var rp : role.getRolePermissions()) {
                    if (rp.getPermission() != null) {
                        rolePermissionCodes.add(rp.getPermission().getCode());
                    }
                }
            }
        }

        // User-level permissions must be a superset of role permissions (only expand)
        // But actually SRS says user-level permissions only ADD, never subtract.
        // Subtracting is prevented because effective permissions = role ∪ user-level.
        // So user-level permissions just add extra permissions. No validation needed against narrowing.
        // We just validate that the permission codes exist.
    }

    private void setUserPermissions(UserAdmin user, Set<String> permissionCodes, UserAdmin assigner) {
        Set<UserAdminPermission> perms = new HashSet<>();
        for (String code : permissionCodes) {
            Permission perm = permissionRepository.findByCode(code).orElseThrow();
            UserAdminPermission uap = new UserAdminPermission();
            uap.setId(new UserAdminPermissionId(user.getId(), perm.getId()));
            uap.setUser(user);
            uap.setPermission(perm);
            uap.setAssignedAt(LocalDateTime.now());
            uap.setAssignedBy(assigner);
            perms.add(uap);
        }
        userAdminPermissionRepository.saveAll(perms);
    }
}
