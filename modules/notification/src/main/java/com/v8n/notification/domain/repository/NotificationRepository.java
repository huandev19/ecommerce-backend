package com.v8n.notification.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.notification.domain.entity.Notification;
import com.v8n.notification.domain.entity.Notification.NotificationStatus;
import com.v8n.notification.domain.entity.Notification.NotificationType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends BaseRepository<Notification, UUID> {

    @Query("SELECT n FROM Notification n WHERE n.recipientId = :recipientId AND n.deletedAt IS NULL ORDER BY n.createdAt DESC")
    List<Notification> findByRecipientId(@Param("recipientId") UUID recipientId);

    @Query("SELECT n FROM Notification n WHERE n.recipientId = :recipientId AND n.status = :status AND n.deletedAt IS NULL")
    List<Notification> findByRecipientIdAndStatus(@Param("recipientId") UUID recipientId, @Param("status") NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.status = :status AND n.deletedAt IS NULL")
    List<Notification> findByStatus(@Param("status") NotificationStatus status);

    @Query("SELECT n FROM Notification n WHERE n.status = 'PENDING' AND n.deletedAt IS NULL")
    List<Notification> findAllPending();

    @Query("SELECT n FROM Notification n WHERE n.channel = :channel AND n.status = 'PENDING' AND n.deletedAt IS NULL")
    List<Notification> findPendingByChannel(@Param("channel") com.v8n.notification.domain.entity.Notification.Channel channel);

    @Query("SELECT n FROM Notification n WHERE n.type = :type AND n.status = 'PENDING' AND n.deletedAt IS NULL")
    List<Notification> findPendingByType(@Param("type") NotificationType type);
}