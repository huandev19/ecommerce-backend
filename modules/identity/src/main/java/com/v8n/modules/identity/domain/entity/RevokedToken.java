package com.v8n.modules.identity.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "revoked_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36)
    private UUID id;

    @Column(name = "token_hash", nullable = false, length = 64)
    private String tokenHash;

    @Column(name = "token_type", nullable = false, length = 20)
    private String tokenType;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_type", length = 20)
    private String userType;

    @Column(name = "revoked_at", nullable = false)
    private LocalDateTime revokedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "reason", length = 100)
    private String reason;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;
}
