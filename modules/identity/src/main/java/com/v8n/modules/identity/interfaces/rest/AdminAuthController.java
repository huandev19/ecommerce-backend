package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.identity.application.dto.AdminAuthResponse;
import com.v8n.modules.identity.application.dto.AdminLoginRequest;
import com.v8n.modules.identity.application.dto.LogoutRequest;
import com.v8n.modules.identity.application.service.AdminAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth/admin")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AdminAuthResponse>> login(
            @Valid @RequestBody AdminLoginRequest request,
            HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        String deviceId = httpRequest.getHeader("X-Device-Id");
        AdminAuthResponse response = adminAuthService.login(request, ipAddress, userAgent, deviceId);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<AdminAuthResponse>> getCurrentUser(Principal principal) {
        AdminAuthResponse response = adminAuthService.getCurrentUser(principal.getName());
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AdminAuthResponse>> refreshToken(
            @RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid refresh token"));
        }
        String refreshToken = authHeader.substring(7);
        AdminAuthResponse response = adminAuthService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResponse.success("Token refreshed", response));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> logout(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody(required = false) LogoutRequest request,
            Principal principal,
            HttpServletRequest httpRequest) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Invalid access token"));
        }
        String accessToken = authHeader.substring(7);
        String userId = principal.getName();
        String ipAddress = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");

        adminAuthService.logout(accessToken, userId, ipAddress, userAgent);
        return ResponseEntity.ok(ApiResponse.success("Logout successful"));
    }

    @PostMapping("/logout/all")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<String>> logoutAll(
            Principal principal,
            HttpServletRequest httpRequest) {
        String userId = principal.getName();
        String ipAddress = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");

        adminAuthService.logoutAll(userId, ipAddress, userAgent);
        return ResponseEntity.ok(ApiResponse.success("All sessions revoked"));
    }
}
