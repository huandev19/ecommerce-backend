package com.v8n.modules.core.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {

    @Bean
    public AuditorAware<UUID> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }
            // Extract user ID from the principal if available
            // The principal can be a custom UserDetails or a UUID string
            Object principal = authentication.getPrincipal();
            if (principal instanceof String) {
                try {
                    return Optional.of(UUID.fromString((String) principal));
                } catch (IllegalArgumentException e) {
                    return Optional.empty();
                }
            }
            return Optional.empty();
        };
    }
}