package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class RoleDetailResponse {
    private UUID id;
    private String name;
    private String description;
    private Set<String> permissions;
    private List<UserSummary> users;
    private boolean isSystem;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    @Builder
    public static class UserSummary {
        private String id;
        private String email;
        private String firstName;
        private String lastName;
    }
}
