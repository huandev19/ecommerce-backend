package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class RoleResponse {
    private UUID id;
    private String name;
    private String description;
    private Set<String> permissions;
    private long permissionsCount;
    private long usersCount;
    private boolean isSystem;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
