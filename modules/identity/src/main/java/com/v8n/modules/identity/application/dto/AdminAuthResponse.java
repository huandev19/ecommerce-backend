package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class AdminAuthResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private long expiresIn;
    private AdminUserData user;

    @Data
    @Builder
    public static class AdminUserData {
        private String id;
        private String email;
        private String firstName;
        private String lastName;
        private boolean isActive;
        private boolean isActivated;
        private List<RoleSummary> roles;
        private Set<String> permissions;
    }
}
