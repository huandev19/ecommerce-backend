package com.v8n.modules.identity.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String avatarUrl;
    private String status;
    private boolean emailVerified;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
}