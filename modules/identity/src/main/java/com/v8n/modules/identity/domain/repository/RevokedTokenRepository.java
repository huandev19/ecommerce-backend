package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.identity.domain.entity.RevokedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RevokedTokenRepository extends JpaRepository<RevokedToken, UUID> {

    Optional<RevokedToken> findByTokenHash(String tokenHash);

    List<RevokedToken> findByUserIdAndUserType(String userId, String userType);

    void deleteByExpiresAtBefore(LocalDateTime expiry);
}
