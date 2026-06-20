package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.identity.application.dto.ActivationResponse;
import com.v8n.modules.identity.application.dto.SetPasswordRequest;
import com.v8n.modules.identity.application.service.AdminAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth/activate")
@RequiredArgsConstructor
public class ActivateController {

    private final AdminAuthService adminAuthService;

    @GetMapping("/{token}")
    public ResponseEntity<ApiResponse<ActivationResponse>> checkActivation(@PathVariable String token) {
        ActivationResponse response = adminAuthService.checkActivation(token);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, String>>> activate(
            @Valid @RequestBody SetPasswordRequest request,
            HttpServletRequest httpRequest) {
        String ipAddress = httpRequest.getRemoteAddr();
        String userAgent = httpRequest.getHeader("User-Agent");
        adminAuthService.activate(request, ipAddress, userAgent);
        return ResponseEntity.ok(ApiResponse.success(
                "Tài khoản đã được kích hoạt thành công. Vui lòng đăng nhập.",
                Map.of("message", "Tài khoản đã được kích hoạt thành công. Vui lòng đăng nhập.")
        ));
    }
}
