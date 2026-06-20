package com.v8n.modules.cart.domain.entity;

import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.catalog.domain.entity.Region;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartEntityTest {

    private Cart cart;
    private ProductVariant variant1;
    private ProductVariant variant2;
    private Region region;

    @BeforeEach
    void setUp() {
        region = new Region();
        region.setId(UUID.randomUUID());
        region.setCurrencyCode("VND");

        cart = new Cart();
        cart.setId(UUID.randomUUID());
        cart.setRegion(region);
        cart.setCurrencyCode("VND");

        variant1 = new ProductVariant();
        variant1.setId(UUID.randomUUID());

        variant2 = new ProductVariant();
        variant2.setId(UUID.randomUUID());
    }

    @Test
    void testAddItem_success() {
        cart.addItem(variant1, 2, 100000, "Product 1", "thumb1.jpg");
        
        assertEquals(1, cart.getLineItems().size());
        assertEquals(2, cart.getItemCount());
        assertEquals(200000, cart.getSubtotal());
        
        // Add same variant again
        cart.addItem(variant1, 1, 100000, "Product 1", "thumb1.jpg");
        assertEquals(1, cart.getLineItems().size());
        assertEquals(3, cart.getItemCount());
        assertEquals(300000, cart.getSubtotal());
    }

    @Test
    void testAddItem_completedCart_throwsException() {
        cart.markCompleted();
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> 
            cart.addItem(variant1, 1, 100000, "Product 1", "thumb1.jpg")
        );
        assertEquals("Cannot add items to a completed cart", exception.getMessage());
    }

    @Test
    void testRemoveItem_success() {
        cart.addItem(variant1, 2, 100000, "Product 1", "thumb1.jpg");
        cart.addItem(variant2, 1, 50000, "Product 2", "thumb2.jpg");
        
        assertEquals(2, cart.getLineItems().size());
        
        boolean removed = cart.removeItemByVariantId(variant1.getId());
        
        assertTrue(removed);
        assertEquals(1, cart.getLineItems().size());
        assertEquals(50000, cart.getSubtotal());
    }

    @Test
    void testClearCart_emptiesItems() {
        cart.addItem(variant1, 2, 100000, "Product 1", "thumb1.jpg");
        cart.addItem(variant2, 1, 50000, "Product 2", "thumb2.jpg");
        
        assertFalse(cart.isEmpty());
        
        cart.clearItems();
        
        assertTrue(cart.isEmpty());
        assertEquals(0, cart.getItemCount());
        assertEquals(0, cart.getSubtotal());
    }
}
