package com.v8n.modules.identity.infrastructure.scheduling;

import com.v8n.modules.identity.domain.repository.RevokedTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Scheduled task that cleans up expired revoked_token records from the database.
 * Runs daily at 2:00 AM and deletes records where expires_at is older than 7 days.
 */
@Component
@ConditionalOnProperty(
        name = "app.scheduling.token-cleanup.enabled",
        havingValue = "true",
        matchIfMissing = true   // mặc định vẫn chạy nếu không có property
)
public class TokenCleanupScheduler {

    private static final Logger log = LoggerFactory.getLogger(TokenCleanupScheduler.class);

    private static final int RETENTION_DAYS = 7;

    private final RevokedTokenRepository revokedTokenRepository;

    public TokenCleanupScheduler(RevokedTokenRepository revokedTokenRepository) {
        this.revokedTokenRepository = revokedTokenRepository;
    }

    /**
     * Runs daily at 2:00 AM to delete expired revoked_token records.
     * Deletes records where expires_at is older than the retention period (7 days).
     */
    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void cleanupExpiredRevokedTokens() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusDays(RETENTION_DAYS);

        log.info("Starting cleanup of revoked tokens expired before {} (retention: {} days)",
                cutoffTime, RETENTION_DAYS);

        revokedTokenRepository.deleteByExpiresAtBefore(cutoffTime);

        log.info("Completed cleanup of expired revoked tokens");
    }
}
