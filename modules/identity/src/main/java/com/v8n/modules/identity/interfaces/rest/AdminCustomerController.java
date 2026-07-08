package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.core.application.dto.PageResponse;
import com.v8n.modules.identity.application.dto.AdminCustomerResponse;
import com.v8n.modules.identity.application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/admin/customers")
@RequiredArgsConstructor
public class AdminCustomerController {

    private final CustomerService customerService;

    @GetMapping
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ApiResponse<PageResponse<AdminCustomerResponse>>> listCustomers(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        PageResponse<AdminCustomerResponse> result = customerService.listCustomers(q, page, size);
        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @PostMapping("/{id}/revoke-tokens")
    @PreAuthorize("hasAuthority('user:update')")
    public ResponseEntity<ApiResponse<Void>> revokeTokens(
            @PathVariable String id,
            Principal principal) {
        customerService.revokeTokens(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
