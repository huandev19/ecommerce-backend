package com.v8n.modules.identity.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoleSummary {
    private String name;
    private String desc;
}
