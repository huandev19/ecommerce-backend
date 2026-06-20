package com.v8n.modules.identity.application.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class UpdateUserRolesRequest {
    @NotEmpty
    private List<String> roleIds;
}
