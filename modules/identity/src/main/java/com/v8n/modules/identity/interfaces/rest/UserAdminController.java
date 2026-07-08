package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.identity.application.dto.AdminUserResponse;
import com.v8n.modules.identity.application.dto.CreateUserAdminRequest;
import com.v8n.modules.identity.application.dto.UnlockResponse;
import com.v8n.modules.identity.application.dto.UpdateUserAdminRequest;
import com.v8n.modules.identity.application.dto.UpdateUserPermissionsRequest;
import com.v8n.modules.identity.application.dto.UpdateUserRolesRequest;
import com.v8n.modules.identity.application.service.UserAdminService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserAdminService userAdminService;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ApiResponse<List<AdminUserResponse>>> list(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String status) {
        List<AdminUserResponse> users = userAdminService.listUsers(q, status);
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('user:create')")
    public ResponseEntity<ApiResponse<AdminUserResponse>> create(
            @Valid @RequestBody CreateUserAdminRequest request,
            Principal principal) {
        AdminUserResponse response = userAdminService.createUser(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ApiResponse<AdminUserResponse>> get(@PathVariable String id) {
        AdminUserResponse response = userAdminService.getUser(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiResponse<AdminUserResponse>> update(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserAdminRequest request,
            Principal principal) {
        AdminUserResponse response = userAdminService.updateUser(id, request, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}/roles")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updateRoles(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRolesRequest request,
            Principal principal) {
        AdminUserResponse response = userAdminService.updateRoles(id, request.getRoleIds(), principal.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('user:delete')")
    public ResponseEntity<Void> delete(
            @PathVariable String id,
            Principal principal) {
        userAdminService.deactivateUser(id, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/unlock")
    @PreAuthorize("hasAuthority('user:unlock')")
    public ResponseEntity<ApiResponse<UnlockResponse>> unlock(
            @PathVariable String id,
            Principal principal) {
        UnlockResponse response = userAdminService.unlockUser(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{id}/permissions")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiResponse<AdminUserResponse>> updatePermissions(
            @PathVariable String id,
            @RequestBody UpdateUserPermissionsRequest request,
            Principal principal) {
        AdminUserResponse response = userAdminService.updatePermissions(
                id,
                request.getPermissions() != null ? request.getPermissions() : java.util.Collections.emptySet(),
                principal.getName()
        );
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/resend-activation")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiResponse<AdminUserResponse>> resendActivation(
            @PathVariable String id,
            Principal principal) {
        AdminUserResponse response = userAdminService.resendActivation(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{id}/revoke-tokens")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiResponse<Void>> revokeTokens(
            @PathVariable String id,
            Principal principal) {
        userAdminService.revokeTokens(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
