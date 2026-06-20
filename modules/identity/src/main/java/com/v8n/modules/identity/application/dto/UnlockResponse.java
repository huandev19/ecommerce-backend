package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UnlockResponse {
    private String id;
    private String email;
    private LocalDateTime lockedUntil;
    private int failedLoginAttempts;
    private LocalDateTime unlockedAt;
    private String unlockedBy;
}
