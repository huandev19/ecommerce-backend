package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.LoginHistoryResponse;
import com.v8n.modules.identity.application.service.LoginHistoryService;
import com.v8n.modules.identity.domain.enums.LoginStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class LoginHistoryController {

    private final LoginHistoryService loginHistoryService;

    @GetMapping("/login-history")
    @PreAuthorize("hasAuthority('login_history:read')")
    public ResponseEntity<Map<String, Object>> list(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to,
            @RequestParam(defaultValue = "0") int offset,
            @RequestParam(defaultValue = "20") int limit) {

        LoginStatus loginStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                loginStatus = LoginStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "Invalid status value: " + status);
            }
        }

        LocalDateTime fromDate = from != null ? LocalDateTime.parse(from) : null;
        LocalDateTime toDate = to != null ? LocalDateTime.parse(to) : null;

        Page<LoginHistoryResponse> page = loginHistoryService.listLoginHistory(
                email, loginStatus, fromDate, toDate, offset, limit);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "data", page.getContent(),
                "count", page.getTotalElements(),
                "offset", offset,
                "limit", limit
        ));
    }

    @GetMapping("/login-history/export")
    @PreAuthorize("hasAuthority('login_history:export')")
    public ResponseEntity<String> export(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            @RequestParam String from,
            @RequestParam String to) {

        if (from == null || to == null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Date range from and to are required");
        }

        LoginStatus loginStatus = null;
        if (status != null && !status.isBlank()) {
            try {
                loginStatus = LoginStatus.valueOf(status.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BusinessException(ErrorCode.INVALID_REQUEST, "Invalid status value: " + status);
            }
        }

        LocalDateTime fromDate = LocalDateTime.parse(from);
        LocalDateTime toDate = LocalDateTime.parse(to);

        String csv = loginHistoryService.exportCsv(email, loginStatus, fromDate, toDate);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=login_history.csv")
                .contentType(MediaType.TEXT_PLAIN)
                .body(csv);
    }
}
