package com.v8n.modules.identity.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponse {
    private UUID id;
    private UUID userId;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private String phone;
    private String company;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}