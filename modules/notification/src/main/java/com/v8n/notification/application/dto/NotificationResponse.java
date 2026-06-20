package com.v8n.notification.application.dto;

import com.v8n.notification.domain.entity.Notification.Channel;
import com.v8n.notification.domain.entity.Notification.NotificationStatus;
import com.v8n.notification.domain.entity.Notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationResponse {
    private UUID id;
    private UUID recipientId;
    private String recipientEmail;
    private NotificationType type;
    private Channel channel;
    private String subject;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}