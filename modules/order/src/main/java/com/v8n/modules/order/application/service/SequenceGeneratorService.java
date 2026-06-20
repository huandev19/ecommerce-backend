package com.v8n.modules.order.application.service;

import com.v8n.modules.order.domain.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SequenceGeneratorService {

    private final OrderRepository orderRepository;

    public Long generateOrderDisplayId() {
        Long maxId = orderRepository.findMaxDisplayId();
        return maxId + 1;
    }
}
