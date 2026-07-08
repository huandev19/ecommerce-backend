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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HexFormat;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenBlacklistService {

    private static final String BLACKLIST_KEY_PREFIX = "blacklist:";

    private final RevokedTokenRepository revokedTokenRepository;
    private final RedisTemplate<String, String> redisTemplate;
    private final SecretKey jwtSecretKey;

    /**
     * Revoke a token: hash it, persist to database, and cache in Redis with TTL.
     *
     * @param token     the raw JWT token string
     * @param tokenType type of token ("access" or "refresh")
     * @param userId    the user ID owning the token
     * @param userType  type of user ("customer" or "admin")
     * @param reason    reason for revocation (e.g., "logout", "admin_force_logout")
     * @param ipAddress IP address from which the revocation was performed
     */
    public void revoke(String token, String tokenType, String userId, String userType,
                       String reason, String ipAddress) {
        String tokenHash = hashToken(token);
        Date expiresAt = extractExpiration(token);

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

        // Cache in Redis with TTL matching token's remaining lifetime
        if (expiresAt != null) {
            long ttl = expiresAt.getTime() - System.currentTimeMillis();
            if (ttl > 0) {
                redisTemplate.opsForValue().set(
                        BLACKLIST_KEY_PREFIX + tokenHash,
                        "1",
                        ttl,
                        TimeUnit.MILLISECONDS
                );
            }
        }
    }

    /**
     * Check whether a token has been revoked.
     * Checks Redis first (fast path), falls back to database query if not found in cache.
     *
     * @param token the raw JWT token string
     * @return true if the token is revoked, false otherwise
     */
    public boolean isRevoked(String token) {
        String tokenHash = hashToken(token);

        // Fast path: check Redis
        try {
            String cached = redisTemplate.opsForValue().get(BLACKLIST_KEY_PREFIX + tokenHash);
            if (cached != null) {
                return true;
            }
        } catch (Exception e) {
            log.warn("Redis unavailable, falling back to database for token blacklist check: {}", e.getMessage());
        }

        // Fallback: check database
        Optional<RevokedToken> revokedToken = revokedTokenRepository.findByTokenHash(tokenHash);
        return revokedToken.isPresent();
    }

    /**
     * Revoke all tokens for a given user by inserting a marker revocation entry.
     * This indicates that all tokens for this user should be considered revoked.
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
     * Hash the raw JWT token string using SHA-256 and return a hex-encoded string.
     *
     * @param token the raw JWT token string
     * @return hex-encoded SHA-256 hash
     */
    private String hashToken(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashBytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    /**
     * Extract the expiration date from a JWT token.
     *
     * @param token the raw JWT token string
     * @return the expiration date, or null if it cannot be extracted
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
