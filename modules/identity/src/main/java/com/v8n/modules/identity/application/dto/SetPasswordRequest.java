package com.v8n.modules.identity.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SetPasswordRequest {
    @NotBlank
    private String activationToken;

    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank
    private String passwordConfirm;
}
