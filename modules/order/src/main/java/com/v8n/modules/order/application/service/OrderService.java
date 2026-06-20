package com.v8n.modules.order.application.service;

import com.v8n.modules.cart.application.dto.CartResponse;
import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.cart.domain.entity.LineItem;
import com.v8n.modules.cart.domain.repository.CartRepository;
import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.catalog.domain.repository.ProductVariantRepository;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.order.application.service.SequenceGeneratorService;
import com.v8n.modules.identity.domain.entity.Address;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import com.v8n.modules.inventory.application.service.InventoryService;
import com.v8n.modules.order.application.dto.CreateOrderRequest;
import com.v8n.modules.order.application.dto.OrderItemResponse;
import com.v8n.modules.order.application.dto.OrderResponse;
import com.v8n.modules.order.application.dto.OrderStatusHistoryResponse;
import com.v8n.modules.order.application.dto.UpdateOrderStatusRequest;
import com.v8n.modules.order.domain.entity.Order;
import com.v8n.modules.order.domain.entity.OrderItem;
import com.v8n.modules.order.domain.entity.OrderStatus;
import com.v8n.modules.order.domain.entity.OrderStatusHistory;
import com.v8n.modules.order.domain.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductVariantRepository variantRepository;
    private final InventoryService inventoryService;
    private final SequenceGeneratorService sequenceGeneratorService;
    private final OrderMapper orderMapper;

    // ===== Query Methods =====

    public OrderResponse getOrderById(UUID orderId) {
        Order order = orderRepository.findByIdNotDeleted(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        return orderMapper.toResponse(order);
    }

    public List<OrderResponse> getOrdersByCustomerId(UUID customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findByEmail(email);
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());
    }

    // ===== Mutation Methods =====

    /**
     * Create an order from a completed cart.
     * This is the main checkout flow:
     * 1. Validate the cart exists and is complete
     * 2. Validate stock for all line items
     * 3. Reserve inventory
     * 4. Create order with items from cart
     * 5. Mark cart as completed
     */
    @Transactional
    public OrderResponse createOrderFromCart(CreateOrderRequest request) {
        // 1. Fetch and validate the cart
        Cart cart = cartRepository.findByIdNotDeleted(request.getCartId())
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));

        if (cart.getCompletedAt() != null) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cart is already completed");
        }

        if (cart.getLineItems() == null || cart.getLineItems().isEmpty()) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST, "Cannot create order from empty cart");
        }

        // 2. Validate stock for all items
        for (LineItem lineItem : cart.getLineItems()) {
            if (lineItem.getVariant() != null) {
                boolean hasStock = inventoryService.checkAvailability(
                        lineItem.getVariant().getId(), lineItem.getQuantity());
                if (!hasStock) {
                    throw new BusinessException(ErrorCode.INSUFFICIENT_STOCK,
                            "Insufficient stock for variant: " + lineItem.getVariant().getId());
                }
            }
        }

        // 3. Reserve inventory
        for (LineItem lineItem : cart.getLineItems()) {
            if (lineItem.getVariant() != null) {
                inventoryService.reserveInventory(
                        lineItem.getVariant().getId(), lineItem.getQuantity());
            }
        }

        // 4. Create the order
        Order order = new Order();
        order.setDisplayId(sequenceGeneratorService.generateOrderDisplayId());
        order.setRegion(cart.getRegion());
        order.setCustomer(cart.getCustomer());
        order.setEmail(cart.getEmail());
        order.setCurrencyCode(cart.getCurrencyCode());
        order.setShippingAddress(cart.getShippingAddress());
        order.setBillingAddress(cart.getBillingAddress());
        order.setStatus(OrderStatus.PENDING);

        // Copy line items from cart to order
        for (LineItem lineItem : cart.getLineItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setVariant(lineItem.getVariant());
            orderItem.setTitle(lineItem.getTitle());
            orderItem.setSku(lineItem.getVariant() != null ? lineItem.getVariant().getSku() : null);
            orderItem.setQuantity(lineItem.getQuantity());
            orderItem.setUnitPrice(lineItem.getUnitPrice()); // cents
            orderItem.setMetadata(lineItem.getMetadata() != null
                    ? lineItem.getMetadata() : new java.util.HashMap<>());
            order.addItem(orderItem);
        }

        // Recalculate and set totals from cart
        order.setSubtotal((int) cart.getSubtotal());
        order.setDiscountTotal(0);
        order.setShippingTotal(0);
        order.setTaxTotal(0);
        order.setTotal((int) cart.getSubtotal());

        // Add initial status history
        OrderStatusHistory history = new OrderStatusHistory();
        history.setOrder(order);
        history.setFromStatus(null);
        history.setToStatus(OrderStatus.PENDING);
        order.getStatusHistory().add(history);

        // 5. Mark cart as completed
        cart.setCompletedAt(LocalDateTime.now());
        cartRepository.save(cart);

        // Save order
        Order savedOrder = orderRepository.save(order);
        log.info("Order created: id={}, displayId={}, customerId={}",
                savedOrder.getId(), savedOrder.getDisplayId(),
                savedOrder.getCustomer() != null ? savedOrder.getCustomer().getId() : "guest");

        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public OrderResponse updateOrderStatus(UUID orderId, UpdateOrderStatusRequest request) {
        Order order = orderRepository.findByIdNotDeleted(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        OrderStatus targetStatus = OrderStatus.valueOf(request.getStatus());
        try {
            order.transitionStatus(targetStatus);
        } catch (IllegalStateException e) {
            throw new BusinessException(ErrorCode.ORDER_INVALID_STATUS, e.getMessage());
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order status updated: id={}, from={}, to={}",
                orderId, order.getStatus(), targetStatus);

        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public OrderResponse cancelOrder(UUID orderId) {
        Order order = orderRepository.findByIdNotDeleted(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        try {
            order.cancel();
        } catch (IllegalStateException e) {
            throw new BusinessException(ErrorCode.ORDER_CANNOT_CANCEL, e.getMessage());
        }

        // Release inventory reservations
        for (OrderItem item : order.getItems()) {
            if (item.getVariant() != null) {
                inventoryService.releaseReservation(
                        item.getVariant().getId(), item.getQuantity());
            }
        }

        Order savedOrder = orderRepository.save(order);
        log.info("Order cancelled: id={}, displayId={}", savedOrder.getId(), savedOrder.getDisplayId());

        return orderMapper.toResponse(savedOrder);
    }

    @Transactional
    public void deleteOrder(UUID orderId) {
        Order order = orderRepository.findByIdNotDeleted(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        order.setDeletedAt(LocalDateTime.now());
        orderRepository.save(order);
        log.info("Order soft-deleted: id={}", orderId);
    }
}