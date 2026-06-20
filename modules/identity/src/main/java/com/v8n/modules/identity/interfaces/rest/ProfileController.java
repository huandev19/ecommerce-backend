package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.identity.application.dto.CustomerResponse;
import com.v8n.modules.identity.application.dto.UpdateProfileRequest;
import com.v8n.modules.identity.application.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/store/customers/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> getProfile(Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        CustomerResponse customer = customerService.getCustomerByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(customer));
    }

    @PatchMapping
    public ResponseEntity<ApiResponse<CustomerResponse>> updateProfile(
            @Valid @RequestBody UpdateProfileRequest request,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        CustomerResponse customer = customerService.updateProfile(userId, request);
        return ResponseEntity.ok(ApiResponse.success(customer));
    }

    private UUID getUserIdFromAuthentication(Authentication authentication) {
        return (UUID) authentication.getPrincipal();
    }
}