package com.v8n.modules.identity.application.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserAdminRequest {
    @Size(max = 100)
    private String firstName;

    @Size(max = 100)
    private String lastName;

    @Size(max = 50)
    private String phone;

    @Size(max = 500)
    private String avatarUrl;

    private Boolean isActive;
}
