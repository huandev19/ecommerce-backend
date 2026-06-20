package com.v8n.modules.identity.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class CreateRoleRequest {
    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotEmpty(message = "At least one permission is required")
    private Set<String> permissions;
}
