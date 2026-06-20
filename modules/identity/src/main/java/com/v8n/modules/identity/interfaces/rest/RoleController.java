package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.identity.application.dto.CreateRoleRequest;
import com.v8n.modules.identity.application.dto.RoleDetailResponse;
import com.v8n.modules.identity.application.dto.RoleResponse;
import com.v8n.modules.identity.application.dto.UpdateRoleRequest;
import com.v8n.modules.identity.application.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> list() {
        List<RoleResponse> roles = roleService.listRoles();
        return ResponseEntity.ok(ApiResponse.success(roles));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('role:create')")
    public ResponseEntity<ApiResponse<RoleResponse>> create(@Valid @RequestBody CreateRoleRequest request) {
        RoleResponse response = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('role:read')")
    public ResponseEntity<ApiResponse<RoleDetailResponse>> get(@PathVariable UUID id) {
        RoleDetailResponse response = roleService.getRole(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('role:update')")
    public ResponseEntity<ApiResponse<RoleResponse>> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRoleRequest request) {
        RoleResponse response = roleService.updateRole(id, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('role:delete')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
