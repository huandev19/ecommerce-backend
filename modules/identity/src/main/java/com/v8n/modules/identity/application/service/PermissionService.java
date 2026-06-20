package com.v8n.modules.identity.application.service;

import com.v8n.modules.identity.application.dto.PermissionResponse;
import com.v8n.modules.identity.domain.entity.Permission;
import com.v8n.modules.identity.domain.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    /**
     * Trả về master data permissions grouped by resource.
     * Mỗi resource chứa danh sách operation.
     */
    public List<PermissionResponse> getGroupedPermissions() {
        List<Permission> allPermissions = permissionRepository.findAll();

        Map<String, List<String>> grouped = allPermissions.stream()
                .collect(Collectors.groupingBy(
                        Permission::getResource,
                        Collectors.mapping(Permission::getOperation, Collectors.toList())
                ));

        return grouped.entrySet().stream()
                .map(entry -> PermissionResponse.builder()
                        .resource(entry.getKey())
                        .operations(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }
}
