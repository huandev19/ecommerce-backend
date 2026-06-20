package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivationResponse {
    private String email;
    private String firstName;
    private String lastName;
    private boolean tokenValid;
    private LocalDateTime expiresAt;
}
