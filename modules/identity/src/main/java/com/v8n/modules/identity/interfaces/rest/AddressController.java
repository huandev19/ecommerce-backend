package com.v8n.modules.identity.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.identity.application.dto.AddressRequest;
import com.v8n.modules.identity.application.dto.AddressResponse;
import com.v8n.modules.identity.application.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/store/customers/addresses")
@RequiredArgsConstructor
public class AddressController {
    // Khởi tạo Logger gắn với Class hiện tại
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponse>>> getAddresses(Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        List<AddressResponse> addresses = addressService.getAddressesByUserId(userId);
        return ResponseEntity.ok(ApiResponse.success(addresses));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> getAddress(
            @PathVariable UUID addressId,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        AddressResponse address = addressService.getAddressById(addressId, userId);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AddressResponse>> createAddress(
            @Valid @RequestBody AddressRequest request,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        AddressResponse address = addressService.createAddress(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(address));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<ApiResponse<AddressResponse>> updateAddress(
            @PathVariable UUID addressId,
            @Valid @RequestBody AddressRequest request,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        AddressResponse address = addressService.updateAddress(addressId, userId, request);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ApiResponse<Void>> deleteAddress(
            @PathVariable UUID addressId,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        addressService.deleteAddress(addressId, userId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @PatchMapping("/{addressId}/default-shipping")
    public ResponseEntity<ApiResponse<AddressResponse>> setDefaultShipping(
            @PathVariable UUID addressId,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        // convert string addressId to uuid addressId
        UUID addressIdString = UUID.fromString(addressId.toString());
        AddressResponse address = addressService.setDefaultShipping(addressIdString, userId);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    @PatchMapping("/{addressId}/default-billing")
    public ResponseEntity<ApiResponse<AddressResponse>> setDefaultBilling(
            @PathVariable UUID addressId,
            Authentication authentication) {
        UUID userId = getUserIdFromAuthentication(authentication);
        AddressResponse address = addressService.setDefaultBilling(addressId, userId);
        return ResponseEntity.ok(ApiResponse.success(address));
    }

    private UUID getUserIdFromAuthentication(Authentication authentication) {
        logger.info("getUserIdFromAuthentication: {}", authentication.getPrincipal());
        // ba46e86d-fb2d-c9ca-9628-14f268b55a23
        String idUser = authentication.getPrincipal().toString();
        return UUID.fromString(idUser);
    }
}