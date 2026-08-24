package com.v8n.modules.core.domain.event;

import com.v8n.modules.core.infrastructure.util.UuidV7;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public abstract class DomainEvent extends ApplicationEvent {
    private final UUID eventId;
    private final LocalDateTime occurredAt;

    protected DomainEvent(Object source) {
        super(source);
        this.eventId = UuidV7.generate();
        this.occurredAt = LocalDateTime.now();
    }
}
