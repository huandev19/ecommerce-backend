package com.v8n.modules.inventory.interfaces.rest;

import com.v8n.modules.core.application.dto.ApiResponse;
import com.v8n.modules.inventory.application.dto.InventoryItemRequest;
import com.v8n.modules.inventory.application.dto.InventoryItemResponse;
import com.v8n.modules.inventory.application.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/inventory")
@RequiredArgsConstructor
@Tag(name = "Admin Inventory API", description = "Admin APIs for managing inventory levels")
public class AdminInventoryController {

    private final InventoryService inventoryService;

    @Operation(summary = "Get all inventory items")
    @GetMapping
    public ApiResponse<List<InventoryItemResponse>> getAllInventoryItems() {
        return ApiResponse.success(inventoryService.getAllInventoryItems());
    }

    @Operation(summary = "Get inventory item by ID")
    @GetMapping("/{id}")
    public ApiResponse<InventoryItemResponse> getInventoryItemById(@PathVariable UUID id) {
        return ApiResponse.success(inventoryService.getInventoryItemById(id));
    }

    @Operation(summary = "Update inventory item")
    @PutMapping("/{id}")
    public ApiResponse<InventoryItemResponse> updateInventoryItem(@PathVariable UUID id, @Valid @RequestBody InventoryItemRequest request) {
        return ApiResponse.success(inventoryService.updateInventoryItem(id, request));
    }
}
