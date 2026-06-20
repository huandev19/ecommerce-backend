package com.v8n.modules.identity.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secret:}")
    private String jwtSecret;

    @Value("${jwt.access-token-expiration:3600000}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration:604800000}")
    private long refreshTokenExpiration;

    private SecretKey key;

    @PostConstruct
    public void init() {
        if (jwtSecret == null || jwtSecret.isBlank()) {
            jwtSecret = Base64.getEncoder().encodeToString(
                    "v8n-ecommerce-default-secret-key-must-be-changed-in-production-2024".getBytes(StandardCharsets.UTF_8)
            );
        }
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        if (keyBytes.length < 32) {
            keyBytes = java.util.Arrays.copyOf(keyBytes, 32);
        }
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(UUID userId, String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Generate access token cho admin user với actorType và permissions.
     */
    public String generateAccessToken(String userId, String email, String actorType, Set<String> permissions) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(userId)
                .claim("email", email)
                .claim("actor_type", actorType)
                .claim("permissions", permissions != null ? new ArrayList<>(permissions) : Collections.emptyList())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    /**
     * Trích xuất permissions từ JWT claims.
     */
    public List<String> getPermissionsFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        @SuppressWarnings("unchecked")
        List<String> permissions = (List<String>) claims.get("permissions", List.class);
        return permissions != null ? permissions : Collections.emptyList();
    }

    public String generateRefreshToken(UUID userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String userId) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + refreshTokenExpiration);

        return Jwts.builder()
                .subject(userId)
                .claim("type", "refresh")
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public UUID getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return UUID.fromString(claims.getSubject());
    }

    /**
     * Trả về userId dưới dạng String (dùng cho user_admins có TEXT UUID).
     */
    public String getUserIdFromTokenAsString(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public long getAccessTokenExpiration() {
        return accessTokenExpiration;
    }
}