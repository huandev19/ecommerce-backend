package com.v8n.modules.core.application.exception;

public enum ErrorCode {
    // Generic
    RESOURCE_NOT_FOUND("Resource not found"),
    INVALID_REQUEST("Invalid request"),
    INTERNAL_ERROR("Internal server error"),
    ACCESS_DENIED("Access denied"),

    // Auth & Identity
    USER_NOT_FOUND("User not found"),
    CUSTOMER_NOT_FOUND("Customer not found"),
    INVALID_CREDENTIALS("Invalid credentials"),
    EMAIL_ALREADY_EXISTS("Email already exists"),
    ROLE_NOT_FOUND("Role not found"),

    // Cart
    CART_NOT_FOUND("Cart not found"),
    CART_EXPIRED("Cart expired"),
    INVALID_QUANTITY("Invalid quantity"),
    LINE_ITEM_NOT_FOUND("Line item not found"),

    // Inventory
    INSUFFICIENT_STOCK("Insufficient stock"),
    RESERVATION_NOT_FOUND("Reservation not found"),
    INVENTORY_ITEM_NOT_FOUND("Inventory item not found"),

    // Order
    ORDER_NOT_FOUND("Order not found"),
    ORDER_CANNOT_CANCEL("Order cannot be cancelled"),
    ORDER_INVALID_STATUS("Order is in an invalid status for this operation"),

    // Payment
    PAYMENT_FAILED("Payment failed"),
    PAYMENT_NOT_AUTHORIZED("Payment not authorized"),
    PAYMENT_SESSION_NOT_FOUND("Payment session not found"),

    // Fulfillment
    SHIPPING_OPTION_NOT_FOUND("Shipping option not found"),
    SHIPPING_PROFILE_NOT_FOUND("Shipping profile not found"),

    // Promotion
    DISCOUNT_NOT_FOUND("Discount not found"),
    DISCOUNT_EXPIRED("Discount has expired"),
    DISCOUNT_USAGE_LIMIT_REACHED("Discount usage limit reached"),
    DISCOUNT_INVALID("Invalid discount code"),

    // Catalog
    PRODUCT_NOT_FOUND("Product not found"),
    CATEGORY_NOT_FOUND("Category not found"),
    VARIANT_NOT_FOUND("Variant not found"),
    STORE_NOT_FOUND("Store not found"),
    REGION_NOT_FOUND("Region not found");

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
