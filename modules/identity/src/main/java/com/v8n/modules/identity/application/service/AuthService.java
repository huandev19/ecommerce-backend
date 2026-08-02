package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AuthResponse;
import com.v8n.modules.identity.application.dto.CustomerResponse;
import com.v8n.modules.identity.application.dto.LoginRequest;
import com.v8n.modules.identity.application.dto.RegisterRequest;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
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

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        Customer customer = new Customer();
        customer.setEmail(request.getEmail().toLowerCase().trim());
        customer.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setPhone(request.getPhone());
        customer.setStatus(Customer.CustomerStatus.ACTIVE);
        customer.setHasAccount(true);
        customer.setMetadata(new java.util.HashMap<>());

        customer = customerRepository.save(customer);
        log.info("New customer registered: {}", customer.getEmail());

        return buildAuthResponse(customer, null);
    }

    public AuthResponse login(LoginRequest request, String deviceId) {
        Customer customer = customerRepository.findByEmail(request.getEmail().toLowerCase().trim())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_CREDENTIALS));

        if (!customer.isHasAccount()) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Account not registered");
        }

        if (customer.getStatus() == Customer.CustomerStatus.BANNED) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Account is banned");
        }

        if (!passwordEncoder.matches(request.getPassword(), customer.getPasswordHash())) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS);
        }

        customer.setLastLoginAt(LocalDateTime.now());
        customerRepository.save(customer);
        log.info("Customer logged in {}: {}", deviceId != null ? "with device " + deviceId : "(null device)", customer.getEmail());

        return buildAuthResponse(customer, deviceId);
    }

    public CustomerResponse getCurrentUser(String userId) {
        Customer customer = customerRepository.findByIdNotDeleted(java.util.UUID.fromString(userId))
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        return mapToCustomerResponse(customer);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new BusinessException(ErrorCode.INVALID_CREDENTIALS, "Invalid or expired refresh token");
        }

        java.util.UUID userId = jwtTokenProvider.getUserIdFromToken(refreshToken);
        Customer customer = customerRepository.findByIdNotDeleted(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return buildAuthResponse(customer, null);
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

    private AuthResponse buildAuthResponse(Customer customer, String deviceId) {
        String accessToken = jwtTokenProvider.generateAccessToken(customer.getId(), customer.getEmail(), deviceId);
        String refreshToken = jwtTokenProvider.generateRefreshToken(customer.getId());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(jwtTokenProvider.getAccessTokenExpiration() / 1000)
                .user(mapToCustomerResponse(customer))
                .build();
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .avatarUrl(customer.getAvatarUrl())
                .status(customer.getStatus().name())
                .emailVerified(customer.isEmailVerified())
                .hasAccount(customer.isHasAccount())
                .createdAt(customer.getCreatedAt())
                .lastLoginAt(customer.getLastLoginAt())
                .build();
    }
}
