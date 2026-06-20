package com.v8n.modules.order.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.order.application.dto.CreateOrderRequest;
import com.v8n.modules.order.application.dto.OrderResponse;
import com.v8n.modules.order.application.dto.UpdateOrderStatusRequest;
import com.v8n.modules.order.application.service.OrderService;
import com.v8n.modules.order.domain.entity.OrderStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    // ===== Query Endpoints =====

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrder(@PathVariable UUID orderId) {
        OrderResponse response = orderService.getOrderById(orderId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> responses = orderService.getAllOrders();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/by-customer")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByCustomer(
            @RequestParam UUID customerId) {
        List<OrderResponse> responses = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByEmail(
            @RequestParam String email) {
        List<OrderResponse> responses = orderService.getOrdersByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/by-status")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getOrdersByStatus(
            @RequestParam OrderStatus status) {
        List<OrderResponse> responses = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    // ===== Mutation Endpoints =====

    @PostMapping("/from-cart")
    public ResponseEntity<ApiResponse<OrderResponse>> createOrderFromCart(
            @Valid @RequestBody CreateOrderRequest request) {
        OrderResponse response = orderService.createOrderFromCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrderStatus(
            @PathVariable UUID orderId,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        OrderResponse response = orderService.updateOrderStatus(orderId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancelOrder(@PathVariable UUID orderId) {
        OrderResponse response = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}