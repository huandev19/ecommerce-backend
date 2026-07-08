package com.v8n.modules.identity.infrastructure.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Configuration
public class JwtKeyConfig {

    @Value("${jwt.secret:}")
    private String jwtSecret;

    @Bean
    public SecretKey jwtSecretKey() {
        String secret = jwtSecret;
        if (secret == null || secret.isBlank()) {
            secret = Base64.getEncoder().encodeToString(
                    "v8n-ecommerce-default-secret-key-must-be-changed-in-production-2024".getBytes(StandardCharsets.UTF_8)
            );
        }
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        if (keyBytes.length < 32) {
            keyBytes = java.util.Arrays.copyOf(keyBytes, 32);
        }
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
