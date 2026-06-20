package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginHistoryResponse {
    private String id;
    private String email;
    private String status;
    private String failureReason;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime attemptedAt;
}
