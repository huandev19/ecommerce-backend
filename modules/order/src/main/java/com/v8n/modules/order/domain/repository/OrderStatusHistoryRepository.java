package com.v8n.modules.order.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.order.domain.entity.OrderStatusHistory;

import java.util.List;
import java.util.UUID;

public interface OrderStatusHistoryRepository extends BaseRepository<OrderStatusHistory, UUID> {

    List<OrderStatusHistory> findByOrderIdOrderByCreatedAtDesc(UUID orderId);
}
