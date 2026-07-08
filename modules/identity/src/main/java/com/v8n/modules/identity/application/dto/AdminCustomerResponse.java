package com.v8n.modules.identity.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.v8n.modules.identity.domain.entity.User.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCustomerResponse {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String fullName;
    private UserStatus status;
    private LocalDateTime createdAt;
}
