package com.v8n.modules.cart.interfaces.rest;

import com.v8n.modules.cart.application.dto.AddLineItemRequest;
import com.v8n.modules.cart.application.dto.CartResponse;
import com.v8n.modules.cart.application.dto.CreateCartRequest;
import com.v8n.modules.cart.application.dto.UpdateLineItemRequest;
import com.v8n.modules.cart.application.service.CartService;
import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AddressRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ===== Query Endpoints =====

    @GetMapping("/{cartId}")
    public ResponseEntity<ApiResponse<CartResponse>> getCart(@PathVariable UUID cartId) {
        CartResponse response = cartService.getCartById(cartId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse<CartResponse>> getActiveCart(@RequestParam UUID customerId) {
        CartResponse response = cartService.getActiveCartByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    // ===== Mutation Endpoints =====

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> createCart(@Valid @RequestBody CreateCartRequest request) {
        CartResponse response = cartService.createCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(response));
    }

    @PostMapping("/{cartId}/line-items")
    public ResponseEntity<ApiResponse<CartResponse>> addLineItem(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddLineItemRequest request) {
        CartResponse response = cartService.addLineItem(cartId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{cartId}/line-items/{lineItemId}")
    public ResponseEntity<ApiResponse<CartResponse>> updateLineItemQuantity(
            @PathVariable UUID cartId,
            @PathVariable UUID lineItemId,
            @Valid @RequestBody UpdateLineItemRequest request) {
        CartResponse response = cartService.updateLineItemQuantity(cartId, lineItemId, request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{cartId}/line-items/{lineItemId}")
    public ResponseEntity<ApiResponse<CartResponse>> removeLineItem(
            @PathVariable UUID cartId,
            @PathVariable UUID lineItemId) {
        CartResponse response = cartService.removeLineItem(cartId, lineItemId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{cartId}/line-items")
    public ResponseEntity<ApiResponse<CartResponse>> clearCart(@PathVariable UUID cartId) {
        CartResponse response = cartService.clearCart(cartId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{cartId}/shipping-address")
    public ResponseEntity<ApiResponse<CartResponse>> updateShippingAddress(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddressRequest addressRequest) {
        CartResponse response = cartService.updateShippingAddress(cartId, addressRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PutMapping("/{cartId}/billing-address")
    public ResponseEntity<ApiResponse<CartResponse>> updateBillingAddress(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddressRequest addressRequest) {
        CartResponse response = cartService.updateBillingAddress(cartId, addressRequest);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> deleteCart(@PathVariable UUID cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }
}