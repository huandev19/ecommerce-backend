package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.ActivationResponse;
import com.v8n.modules.identity.application.dto.AdminAuthResponse;
import com.v8n.modules.identity.application.dto.AdminLoginRequest;
import com.v8n.modules.identity.application.dto.RoleSummary;
import com.v8n.modules.identity.application.dto.SetPasswordRequest;
import com.v8n.modules.identity.domain.entity.LoginHistory;
import com.v8n.modules.identity.domain.entity.Role;
import com.v8n.modules.identity.domain.entity.UserAdmin;
import com.v8n.modules.identity.domain.entity.UserAdminRole;
import com.v8n.modules.identity.domain.enums.LoginStatus;
import com.v8n.modules.identity.domain.repository.LoginHistoryRepository;
import com.v8n.modules.identity.domain.repository.UserAdminRepository;
import com.v8n.modules.identity.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCKOUT_DURATION_MINUTES = 15;
    private static final int ACTIVATION_TOKEN_HOURS = 24;

    private final UserAdminRepository userAdminRepository;
    private final LoginHistoryRepository loginHistoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Admin login with lockout flow.
     */
    @Transactional
    public AdminAuthResponse login(AdminLoginRequest request, String ipAddress, String userAgent) {
        String email = request.getEmail().toLowerCase().trim();

        // 1. Find user by email (active only)
        UserAdmin user = userAdminRepository.findByEmailActive(email)
                .orElseThrow(() -> {
                    // Record failed attempt for unknown user
                    recordLoginHistory(null, email, LoginStatus.FAILED, "INVALID_CREDENTIALS", ipAddress, userAgent);
                    throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
                });

        // 2. Check if password not set (not activated)
        if (user.getPasswordHash() == null) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Tài khoản chưa được kích hoạt");
        }

        // 3. Check if account is locked
        if (user.isLocked()) {
            recordLoginHistory(user, email, LoginStatus.FAILED, "ACCOUNT_LOCKED", ipAddress, userAgent);
            throw new BusinessException(ErrorCode.ACCESS_DENIED,
                    "Tài khoản bị khóa đến " + user.getLockedUntil().toString());
        }

        // 4. Check password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            int attempts = (user.getFailedLoginAttempts() != null ? user.getFailedLoginAttempts() : 0) + 1;
            user.setFailedLoginAttempts(attempts);

            if (attempts >= MAX_FAILED_ATTEMPTS) {
                user.setLockedUntil(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
                userAdminRepository.save(user);
                recordLoginHistory(user, email, LoginStatus.FAILED, "ACCOUNT_LOCKED", ipAddress, userAgent);
                throw new BusinessException(ErrorCode.ACCESS_DENIED,
                        "Tài khoản bị khóa " + LOCKOUT_DURATION_MINUTES + " phút do đăng nhập sai quá " + MAX_FAILED_ATTEMPTS + " lần");
            }

            userAdminRepository.save(user);
            recordLoginHistory(user, email, LoginStatus.FAILED, "INVALID_CREDENTIALS", ipAddress, userAgent);
            int remaining = MAX_FAILED_ATTEMPTS - attempts;
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS,
                    "Sai mật khẩu. Còn " + remaining + " lần thử.");
        }

        // 5. Success: reset failed attempts, update last login
        user.setFailedLoginAttempts(0);
        user.setLockedUntil(null);
        user.setLastLoginAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userAdminRepository.save(user);

        recordLoginHistory(user, email, LoginStatus.SUCCESS, null, ipAddress, userAgent);

        // Generate JWT with permissions
        Set<String> permissions = user.getEffectivePermissions();
        String accessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getEmail(), "admin", permissions);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        // Build response
        List<RoleSummary> roles = Collections.emptyList();
        if (user.getUserAdminRoles() != null) {
            roles = user.getUserAdminRoles().stream()
                    .map(uar -> {
                        Role role = uar.getRole();
                        return RoleSummary.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build();
                    })
                    .collect(Collectors.toList());
        }

        return AdminAuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
                .user(AdminAuthResponse.AdminUserData.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .isActive(user.getIsActive() != null && user.getIsActive())
                        .isActivated(user.isActivated())
                        .roles(roles)
                        .permissions(permissions)
                        .build())
                .build();
    }

    /**
     * Refresh token for admin user.
     */
    @Transactional(readOnly = true)
    public AdminAuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Invalid or expired refresh token");
        }

        String userId = jwtTokenProvider.getUserIdFromTokenAsString(refreshToken);
        UserAdmin user = userAdminRepository.findByIdActive(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Set<String> permissions = user.getEffectivePermissions();
        String newAccessToken = jwtTokenProvider.generateAccessToken(
                user.getId(), user.getEmail(), "admin", permissions);

        return AdminAuthResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
                .build();
    }

    /**
     * Get current admin user info.
     */
    @Transactional(readOnly = true)
    public AdminAuthResponse getCurrentUser(String userId) {
        UserAdmin user = userAdminRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Set<String> permissions = user.getEffectivePermissions();

        List<RoleSummary> roles = Collections.emptyList();
        if (user.getUserAdminRoles() != null) {
            roles = user.getUserAdminRoles().stream()
                    .map(uar -> {
                        Role role = uar.getRole();
                        return RoleSummary.builder()
                                .id(role.getId())
                                .name(role.getName())
                                .build();
                    })
                    .collect(Collectors.toList());
        }

        return AdminAuthResponse.builder()
                .user(AdminAuthResponse.AdminUserData.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .isActive(user.getIsActive() != null && user.getIsActive())
                        .isActivated(user.isActivated())
                        .roles(roles)
                        .permissions(permissions)
                        .build())
                .build();
    }

    /**
     * Check activation token validity.
     */
    @Transactional(readOnly = true)
    public ActivationResponse checkActivation(String token) {
        UUID activationToken;
        try {
            activationToken = UUID.fromString(token);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Token không hợp lệ");
        }

        UserAdmin user = userAdminRepository.findByActivationToken(activationToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST, "Token không tồn tại"));

        if (user.isActivated()) {
            // Already activated — return but token is no longer valid
            return ActivationResponse.builder()
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .tokenValid(false)
                    .expiresAt(null)
                    .build();
        }

        // Check 24h expiry (activation token issued at user creation time)
        LocalDateTime expiresAt = user.getCreatedAt().plusHours(ACTIVATION_TOKEN_HOURS);
        boolean isValid = LocalDateTime.now().isBefore(expiresAt);

        return ActivationResponse.builder()
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .tokenValid(isValid)
                .expiresAt(expiresAt)
                .build();
    }

    /**
     * Activate account: set password for the first time.
     */
    @Transactional
    public void activate(SetPasswordRequest request, String ipAddress, String userAgent) {
        // Validate password match
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Mật khẩu xác nhận không khớp");
        }

        // Validate password strength
        if (request.getPassword().length() < 8) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Mật khẩu phải có ít nhất 8 ký tự");
        }

        UUID activationToken;
        try {
            activationToken = UUID.fromString(request.getActivationToken());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Token không hợp lệ");
        }

        UserAdmin user = userAdminRepository.findByActivationToken(activationToken)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_REQUEST, "Token không tồn tại"));

        // Check if already activated
        if (user.isActivated()) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Tài khoản đã được kích hoạt");
        }

        // Check expiry
        LocalDateTime expiresAt = user.getCreatedAt().plusHours(ACTIVATION_TOKEN_HOURS);
        if (LocalDateTime.now().isAfter(expiresAt)) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Token đã hết hạn");
        }

        // Set password
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setPasswordSetAt(LocalDateTime.now());
        user.setActivationToken(null);
        user.setUpdatedAt(LocalDateTime.now());
        userAdminRepository.save(user);

        recordLoginHistory(user, user.getEmail(), LoginStatus.SUCCESS, null, ipAddress, userAgent);
        log.info("Account activated: {}", user.getEmail());
    }

    // --- Private helpers ---

    private void recordLoginHistory(UserAdmin user, String email, LoginStatus status,
                                     String failureReason, String ipAddress, String userAgent) {
        LoginHistory history = new LoginHistory();
        history.setId(UUID.randomUUID().toString());
        history.setUserAdmin(user);
        history.setEmail(email);
        history.setStatus(status);
        history.setFailureReason(failureReason);
        history.setIpAddress(ipAddress);
        history.setUserAgent(userAgent);
        history.setAttemptedAt(LocalDateTime.now());
        loginHistoryRepository.save(history);
    }
}
