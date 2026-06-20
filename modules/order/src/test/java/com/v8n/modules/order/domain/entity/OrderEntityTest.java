package com.v8n.modules.order.domain.entity;

import com.v8n.modules.catalog.domain.entity.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderEntityTest {

    private Order order;
    private OrderItem item1;
    private OrderItem item2;

    @BeforeEach
    void setUp() {
        Region region = new Region();
        region.setId(UUID.randomUUID());
        
        order = new Order();
        order.setId(UUID.randomUUID());
        order.setRegion(region);
        order.setCurrencyCode("VND");

        item1 = new OrderItem();
        item1.setId(UUID.randomUUID());
        item1.setUnitPrice(100000);
        item1.setQuantity(2); // subtotal = 200,000

        item2 = new OrderItem();
        item2.setId(UUID.randomUUID());
        item2.setUnitPrice(50000);
        item2.setQuantity(1); // subtotal = 50,000
    }

    @Test
    void testAddItem_recalculatesTotals() {
        order.addItem(item1);
        
        assertEquals(1, order.getItems().size());
        assertEquals(200000, order.getSubtotal());
        assertEquals(200000, order.getTotal());
        
        order.setShippingTotal(15000);
        order.addItem(item2);
        
        assertEquals(2, order.getItems().size());
        assertEquals(250000, order.getSubtotal());
        assertEquals(265000, order.getTotal()); // 250k + 15k shipping
    }

    @Test
    void testCancelOrder_cancellableStatus_success() {
        order.setStatus(OrderStatus.PENDING);
        order.cancel();
        
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
        assertNotNull(order.getCanceledAt());
    }

    @Test
    void testCancelOrder_nonCancellable_throwsException() {
        order.setStatus(OrderStatus.SHIPPED);
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            order.cancel()
        );
        assertTrue(exception.getMessage().contains("Cannot cancel order"));
    }

    @Test
    void testStatusTransition_validTransitions() {
        assertEquals(OrderStatus.PENDING, order.getStatus());
        assertEquals(0, order.getStatusHistory().size());
        
        order.transitionStatus(OrderStatus.CONFIRMED);
        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(1, order.getStatusHistory().size());
        assertEquals(OrderStatus.PENDING, order.getStatusHistory().get(0).getFromStatus());
        assertEquals(OrderStatus.CONFIRMED, order.getStatusHistory().get(0).getToStatus());
        
        order.transitionStatus(OrderStatus.PROCESSING);
        assertEquals(OrderStatus.PROCESSING, order.getStatus());
        assertEquals(2, order.getStatusHistory().size());
    }

    @Test
    void testStatusTransition_invalidTransitions_throwsException() {
        order.setStatus(OrderStatus.CANCELLED);
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            order.transitionStatus(OrderStatus.CONFIRMED)
        );
        assertTrue(exception.getMessage().contains("Cannot transition from CANCELLED"));
    }
}
