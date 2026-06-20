package com.v8n.modules.identity.application.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateUserPermissionsRequest {
    private Set<String> permissions;
}
