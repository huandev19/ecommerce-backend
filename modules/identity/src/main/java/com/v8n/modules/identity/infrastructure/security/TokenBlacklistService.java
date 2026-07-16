package com.v8n.modules.identity.infrastructure.security;

import com.v8n.modules.identity.domain.entity.RevokedToken;
import com.v8n.modules.identity.domain.repository.RevokedTokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private static final String LOGOUT_KEY_PREFIX = "logout:";
    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

    private final RevokedTokenRepository revokedTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final SecretKey jwtSecretKey;

    /**
     * Logout a specific device session for a user.
     * Stores a device-scoped marker in the format: logout:ALL_{userId}_{userType}_{deviceId}
     * The deviceId is extracted from the JWT claims internally.
     *
     * @param token     the raw JWT token string (used to extract deviceId and expiration)
     * @param userId    the user ID owning the token
     * @param userType  type of user ("customer" or "admin")
     * @param reason    reason for revocation (e.g., "USER_LOGOUT")
     * @param ipAddress IP address from which the revocation was performed
     */
    public void logout(String token, String userId, String userType,
                       String reason, String ipAddress) {
        try {
            String deviceId = extractDeviceId(token);
            String markerHash = buildDeviceMarker(userId, userType, deviceId);
            Date expiresAt = extractExpiration(token);

            saveRevokedToken(markerHash, "access", userId, userType, expiresAt, reason, ipAddress);

            // Cache in Redis with TTL matching token's remaining lifetime
            if (expiresAt != null) {
                long ttl = expiresAt.getTime() - System.currentTimeMillis();
                if (ttl > 0) {
                    redisTemplate.opsForValue().set(
                            LOGOUT_KEY_PREFIX + markerHash,
                            "1",
                            ttl,
                            TimeUnit.MILLISECONDS
                    );
                }
            }
        } catch (Exception e) {
            log.error("Failed to logout token for user {}: {}", userId, e.getMessage());
        }
    }

    /**
     * Revoke all tokens for a user across all devices (admin force-revoke).
     * Stores a user-scoped marker in the format: blacklist:ALL_{userId}_{userType}
     *
     * @param userId   the user ID
     * @param userType type of user ("customer" or "admin")
     * @param reason   reason for revocation
     */
    public void revokeAllForUser(String userId, String userType, String reason) {
        String markerHash = "ALL_" + userId + "_" + userType;

        RevokedToken marker = RevokedToken.builder()
                .tokenHash(markerHash)
                .tokenType("access")
                .userId(userId)
                .userType(userType)
                .revokedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusDays(30))
                .reason(reason)
                .build();

        revokedTokenRepository.save(marker);

        // Cache in Redis with long TTL (30 days)
        redisTemplate.opsForValue().set(
                BLACKLIST_KEY_PREFIX + markerHash,
                "1",
                30,
                TimeUnit.DAYS
        );
    }

    /**
     * Check whether a token has been revoked.
     * Checks two markers in order:
     * <ol>
     *   <li>Device-scoped logout marker: {@code logout:ALL_{userId}_{userType}_{deviceId}}</li>
     *   <li>User-scoped force-revoke marker: {@code blacklist:ALL_{userId}_{userType}}</li>
     * </ol>
     * Redis is checked first (fast path), falls back to database query if not found in cache.
     *
     * @param token the raw JWT token string
     * @return true if the token is revoked, false otherwise
     */
    public boolean isRevoked(String token) {
        String userId = extractUserId(token);
        String userType = extractUserType(token);
        String deviceId = extractDeviceId(token);

        if (userId == null || userType == null) {
            return false;
        }

        // 1) Check device-scoped logout marker: logout:ALL_{userId}_{userType}_{deviceId}
        String deviceMarker = buildDeviceMarker(userId, userType, deviceId);

        // Fast path: check Redis
        try {
            String cached = redisTemplate.opsForValue().get(LOGOUT_KEY_PREFIX + deviceMarker);
            if (cached != null) {
                return true;
            }
        } catch (Exception e) {
            log.warn("Redis unavailable, falling back to database for logout marker check: {}", e.getMessage());
        }

        // Fallback: check database
        Optional<RevokedToken> revokedToken = revokedTokenRepository.findByTokenHash(deviceMarker);
        if (revokedToken.isPresent()) {
            return true;
        }

        // 2) Check user-scoped force-revoke marker: blacklist:ALL_{userId}_{userType}
        String userMarker = "ALL_" + userId + "_" + userType;

        // Fast path: check Redis
        try {
            String cached = redisTemplate.opsForValue().get(BLACKLIST_KEY_PREFIX + userMarker);
            if (cached != null) {
                return true;
            }
        } catch (Exception e) {
            log.warn("Redis unavailable, falling back to database for blacklist marker check: {}", e.getMessage());
        }

        // Fallback: check database
        return revokedTokenRepository.findByTokenHash(userMarker).isPresent();
    }

    // ========== Private helpers ==========

    /**
     * Build a device-scoped marker: ALL_{userId}_{userType}_{deviceId}.
     * When deviceId is null, the marker becomes ALL_{userId}_{userType}_null.
     */
    private String buildDeviceMarker(String userId, String userType, String deviceId) {
        return "ALL_" + userId + "_" + userType + "_" + (deviceId != null ? deviceId : "null");
    }

    /**
     * Save a revoked token record to the database.
     */
    private void saveRevokedToken(String tokenHash, String tokenType, String userId, String userType,
                                  Date expiresAt, String reason, String ipAddress) {
        RevokedToken revokedToken = RevokedToken.builder()
                .tokenHash(tokenHash)
                .tokenType(tokenType)
                .userId(userId)
                .userType(userType)
                .revokedAt(LocalDateTime.now())
                .expiresAt(expiresAt != null
                        ? expiresAt.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                        : LocalDateTime.now())
                .reason(reason)
                .ipAddress(ipAddress)
                .build();

        revokedTokenRepository.save(revokedToken);
    }

    /**
     * Extract the userId (subject) from a JWT token.
     */
    private String extractUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            log.warn("Failed to extract user id from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Extract the user type ("customer" or "admin") from a JWT token.
     * <p>
     * Admin tokens carry an {@code actor_type} claim; customer tokens do not,
     * so we default to {@code "customer"}.
     */
    private String extractUserType(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String actorType = claims.get("actor_type", String.class);
            return actorType != null ? actorType : "customer";
        } catch (Exception e) {
            log.warn("Failed to extract user type from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Extract the deviceId from a JWT token.
     * <p>
     * Tokens without a {@code device_id} claim return {@code null} (backward compatible).
     */
    private String extractDeviceId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("device_id", String.class);
        } catch (Exception e) {
            log.warn("Failed to extract device id from token: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Extract the expiration date from a JWT token.
     */
    private Date extractExpiration(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getExpiration();
        } catch (Exception e) {
            log.warn("Failed to extract expiration from token: {}", e.getMessage());
            return null;
        }
    }
}
