package com.v8n.fulfillment.interfaces.rest;

import com.v8n.fulfillment.application.dto.FulfillmentRequest;
import com.v8n.fulfillment.application.dto.FulfillmentResponse;
import com.v8n.fulfillment.application.service.FulfillmentService;
import com.v8n.modules.core.application.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin Fulfillment API", description = "Admin APIs for managing order fulfillments")
public class AdminFulfillmentController {

    private final FulfillmentService fulfillmentService;

    @Operation(summary = "Get fulfillments by order ID")
    @GetMapping("/orders/{orderId}/fulfillments")
    public ApiResponse<List<FulfillmentResponse>> getFulfillmentByOrderId(@PathVariable UUID orderId) {
        return ApiResponse.success(fulfillmentService.getFulfillmentByOrderId(orderId));
    }

    @Operation(summary = "Create fulfillment for order")
    @PostMapping("/orders/{orderId}/fulfillments")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<FulfillmentResponse> createFulfillment(
            @PathVariable UUID orderId,
            @Valid @RequestBody FulfillmentRequest request) {
        return ApiResponse.success(fulfillmentService.createFulfillment(orderId, request));
    }

    @Operation(summary = "Mark fulfillment as shipped")
    @PostMapping("/fulfillments/{id}/ship")
    public ApiResponse<FulfillmentResponse> shipFulfillment(
            @PathVariable UUID id,
            @Valid @RequestBody FulfillmentRequest request) {
        return ApiResponse.success(fulfillmentService.shipFulfillment(id, request));
    }

    @Operation(summary = "Mark fulfillment as delivered")
    @PostMapping("/fulfillments/{id}/deliver")
    public ApiResponse<FulfillmentResponse> deliverFulfillment(@PathVariable UUID id) {
        return ApiResponse.success(fulfillmentService.deliverFulfillment(id));
    }

    @Operation(summary = "Cancel fulfillment")
    @PostMapping("/fulfillments/{id}/cancel")
    public ApiResponse<FulfillmentResponse> cancelFulfillment(@PathVariable UUID id) {
        return ApiResponse.success(fulfillmentService.cancelFulfillment(id));
    }

    @Operation(summary = "Return fulfillment")
    @PostMapping("/fulfillments/{id}/return")
    public ApiResponse<FulfillmentResponse> returnFulfillment(
            @PathVariable UUID id,
            @RequestParam int quantity) {
        return ApiResponse.success(fulfillmentService.returnFulfillment(id, quantity));
    }
}
