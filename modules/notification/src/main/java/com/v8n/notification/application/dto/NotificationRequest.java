package com.v8n.notification.application.dto;

import com.v8n.notification.domain.entity.Notification.Channel;
import com.v8n.notification.domain.entity.Notification.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationRequest {
    private UUID recipientId;
    private String recipientEmail;
    private NotificationType type;
    private Channel channel;
    private String subject;
    private String content;
}