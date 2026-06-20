package com.v8n.modules.order.domain.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * JPA AttributeConverter that maps OrderStatus enum to lowercase strings for DB storage.
 * DB stores status values as lowercase (e.g., 'pending', 'cancelled') while JPA
 * enum name() is UPPERCASE.
 */
@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus status) {
        if (status == null) {
            return null;
        }
        return status.name().toLowerCase();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return OrderStatus.valueOf(dbData.toUpperCase());
    }
}