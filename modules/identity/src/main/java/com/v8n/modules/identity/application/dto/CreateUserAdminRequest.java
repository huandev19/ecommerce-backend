package com.v8n.modules.identity.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class CreateUserAdminRequest {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @Size(max = 50)
    private String phone;

    @Size(max = 500)
    private String avatarUrl;

    private Boolean isActive = true;

    private List<String> roleIds;

    private Set<String> permissions;
}
