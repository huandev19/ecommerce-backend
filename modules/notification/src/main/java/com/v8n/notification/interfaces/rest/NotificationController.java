package com.v8n.notification.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.notification.application.dto.NotificationResponse;
import com.v8n.notification.application.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/notifications")
@RequiredArgsConstructor
@Tag(name = "Admin Notification API", description = "Admin APIs for managing notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "Get notification by ID")
    @GetMapping("/{id}")
    public ApiResponse<NotificationResponse> getNotificationById(@PathVariable UUID id) {
        return ApiResponse.success(notificationService.getNotificationById(id));
    }

    @Operation(summary = "Get notifications by recipient ID")
    @GetMapping("/recipient/{recipientId}")
    public ApiResponse<List<NotificationResponse>> getNotificationsByRecipientId(@PathVariable UUID recipientId) {
        return ApiResponse.success(notificationService.getNotificationsByRecipientId(recipientId));
    }

    @Operation(summary = "Mark notification as read")
    @PutMapping("/{id}/read")
    public ApiResponse<NotificationResponse> markAsRead(@PathVariable UUID id) {
        return ApiResponse.success(notificationService.markAsRead(id));
    }
}
