package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AuthResponse;
import com.v8n.modules.identity.application.dto.LoginRequest;
import com.v8n.modules.identity.application.dto.RegisterRequest;
import com.v8n.modules.identity.application.dto.UserResponse;
import com.v8n.modules.identity.domain.entity.User;
import com.v8n.modules.identity.domain.repository.UserRepository;
import com.v8n.modules.identity.infrastructure.security.JwtTokenProvider;
import com.v8n.modules.identity.infrastructure.security.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = new User();
        user.setEmail(request.getEmail().toLowerCase().trim());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setStatus(User.UserStatus.ACTIVE);

        user = userRepository.save(user);
        log.info("New user registered: {}", user.getEmail());

        return buildAuthResponse(user, null);
    }

    public AuthResponse login(LoginRequest request, String deviceId) {
        User user = userRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (user.getStatus() == User.UserStatus.BANNED) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Account is banned");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        log.info("User logged in {}: {}", deviceId != null ? "with device " + deviceId : "(null device)", user.getEmail());

        return buildAuthResponse(user, deviceId);
    }

    public UserResponse getCurrentUser(String userId) {
        User user = userRepository.findByIdNotDeleted(java.util.UUID.fromString(userId))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return mapToUserResponse(user);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Invalid or expired refresh token");
        }

        java.util.UUID userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        User user = userRepository.findByIdNotDeleted(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return buildAuthResponse(user, null);
    }

    /**
     * Logout: revoke the current device session for a customer user.
     */
    public void logout(String accessToken, String userId, String ipAddress) {
        tokenBlacklistService.logout(accessToken, userId, "customer", "USER_LOGOUT", ipAddress);
        log.info("Customer user {} logged out from IP {}", userId, ipAddress);
    }

    /**
     * Logout all sessions: revoke all tokens for the customer user.
     */
    public void logoutAll(String userId, String ipAddress) {
        tokenBlacklistService.revokeAllForUser(userId, "customer", "USER_LOGOUT_ALL");
        log.info("All sessions revoked for customer user {} from IP {}", userId, ipAddress);
    }

    private AuthResponse buildAuthResponse(User user, String deviceId) {
        String accessToken = jwtTokenProvider.generateAccessToken(user.getId(), user.getEmail(), deviceId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
                .user(mapToUserResponse(user))
                .build();
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .fullName(user.getFullName())
                .phone(user.getPhone())
                .avatarUrl(user.getAvatarUrl())
                .status(user.getStatus().name())
                .emailVerified(user.isEmailVerified())
                .createdAt(user.getCreatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
