package com.v8n.modules.identity.application.service;

import com.v8n.modules.identity.application.dto.LoginHistoryResponse;
import com.v8n.modules.identity.domain.entity.LoginHistory;
import com.v8n.modules.identity.domain.entity.UserAdmin;
import com.v8n.modules.identity.domain.enums.LoginStatus;
import com.v8n.modules.identity.domain.repository.LoginHistoryRepository;
import com.v8n.modules.core.infrastructure.util.UuidV7;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoginHistoryService {

    private final LoginHistoryRepository loginHistoryRepository;

    /**
     * Record a login history entry.
     */
    public void recordLoginHistory(UserAdmin user, String email, LoginStatus status,
                                    String failureReason, String ipAddress, String userAgent) {
        LoginHistory history = new LoginHistory();
        history.setId(UuidV7.generateString());
        history.setUserAdmin(user);
        history.setEmail(email);
        history.setStatus(status);
        history.setFailureReason(failureReason);
        history.setIpAddress(ipAddress);
        history.setUserAgent(userAgent);
        history.setAttemptedAt(LocalDateTime.now());
        loginHistoryRepository.save(history);
    }

    /**
     * Lấy danh sách login history có phân trang và lọc.
     */
    public Page<LoginHistoryResponse> listLoginHistory(
            String email, LoginStatus status, LocalDateTime from, LocalDateTime to,
            int page, int size) {
        Pageable pageable = PageRequest.of(page / size, size);
        Page<LoginHistory> pageResult = loginHistoryRepository.findFiltered(email, status, from, to, pageable);
        return pageResult.map(this::toResponse);
    }

    /**
     * Export tất cả records matching filter dưới dạng CSV string.
     */
    public String exportCsv(String email, LoginStatus status, LocalDateTime from, LocalDateTime to) {
        List<LoginHistory> records = loginHistoryRepository.findFilteredAll(email, status, from, to);
        StringWriter writer = new StringWriter();

        // CSV header
        writer.write("id,email,status,failure_reason,ip_address,user_agent,attempted_at\n");

        // CSV rows
        for (LoginHistory lh : records) {
            writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                    escapeCsv(lh.getId()),
                    escapeCsv(lh.getEmail()),
                    lh.getStatus() != null ? lh.getStatus().name() : "",
                    escapeCsv(lh.getFailureReason()),
                    escapeCsv(lh.getIpAddress()),
                    escapeCsv(lh.getUserAgent()),
                    lh.getAttemptedAt() != null ? lh.getAttemptedAt().toString() : ""
            ));
        }

        return writer.toString();
    }

    private LoginHistoryResponse toResponse(LoginHistory lh) {
        return LoginHistoryResponse.builder()
                .id(lh.getId())
                .email(lh.getEmail())
                .status(lh.getStatus() != null ? lh.getStatus().name() : null)
                .failureReason(lh.getFailureReason())
                .ipAddress(lh.getIpAddress())
                .userAgent(lh.getUserAgent())
                .attemptedAt(lh.getAttemptedAt())
                .build();
    }

    private String escapeCsv(String value) {
        if (value == null) return "";
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
