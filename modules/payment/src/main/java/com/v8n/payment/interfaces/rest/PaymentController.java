package com.v8n.payment.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.payment.application.dto.PaymentSessionResponse;
import com.v8n.payment.application.dto.RefundRequest;
import com.v8n.payment.application.dto.RefundResponse;
import com.v8n.payment.application.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Payment API", description = "APIs for managing payments, sessions, and refunds")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Webhook handler for payment providers")
    @PostMapping("/store/payments/webhook")
    public ApiResponse<String> handleWebhook(@RequestBody String payload) {
        // In a real implementation, this would validate the provider signature
        // and call paymentService.confirmSession() or paymentService.capturePayment()
        return ApiResponse.success("Webhook received");
    }

    @Operation(summary = "Capture payment session")
    @PostMapping("/admin/payments/{sessionId}/capture")
    public ApiResponse<PaymentSessionResponse> capturePayment(@PathVariable UUID sessionId) {
        return ApiResponse.success(paymentService.capturePayment(sessionId));
    }

    @Operation(summary = "Refund payment")
    @PostMapping("/admin/payments/{collectionId}/refund")
    public ApiResponse<RefundResponse> createRefund(
            @PathVariable UUID collectionId,
            @Valid @RequestBody RefundRequest request) {
        return ApiResponse.success(paymentService.createRefund(collectionId, request));
    }
}
