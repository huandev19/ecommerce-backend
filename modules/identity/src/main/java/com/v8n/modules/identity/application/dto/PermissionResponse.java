package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PermissionResponse {
    private String resource;
    private List<String> operations;
}
