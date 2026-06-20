package com.v8n.modules.cart.application.service;

import com.v8n.modules.cart.application.dto.CartResponse;
import com.v8n.modules.cart.application.dto.LineItemResponse;
import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.cart.domain.entity.LineItem;
import com.v8n.modules.identity.application.dto.AddressResponse;
import com.v8n.modules.identity.domain.entity.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartMapper {

    public CartResponse toResponse(Cart cart) {
        if (cart == null) {
            return null;
        }

        CartResponse.CartResponseBuilder builder = CartResponse.builder()
                .id(cart.getId())
                .regionId(cart.getRegion() != null ? cart.getRegion().getId() : null)
                .customerId(cart.getCustomer() != null ? cart.getCustomer().getId() : null)
                .email(cart.getEmail())
                .currencyCode(cart.getCurrencyCode())
                .shippingAddress(toAddressResponse(cart.getShippingAddress()))
                .billingAddress(toAddressResponse(cart.getBillingAddress()))
                .completedAt(cart.getCompletedAt())
                .completed(cart.isCompleted())
                .itemCount(cart.getItemCount())
                .metadata(cart.getMetadata())
                .createdAt(cart.getCreatedAt())
                .updatedAt(cart.getUpdatedAt());

        // Calculate subtotal from line items
        long subtotal = 0;
        if (cart.getLineItems() != null) {
            builder.lineItems(cart.getLineItems().stream()
                    .map(this::toLineItemResponse)
                    .toList());
            subtotal = cart.getLineItems().stream()
                    .mapToLong(item -> (long) item.getUnitPrice() * item.getQuantity())
                    .sum();
        }
        builder.subtotal(subtotal);

        return builder.build();
    }

    public LineItemResponse toLineItemResponse(LineItem item) {
        if (item == null) {
            return null;
        }

        return LineItemResponse.builder()
                .id(item.getId())
                .cartId(item.getCart() != null ? item.getCart().getId() : null)
                .variantId(item.getVariant() != null ? item.getVariant().getId() : null)
                .variantSku(item.getVariant() != null ? item.getVariant().getSku() : null)
                .title(item.getTitle())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .subtotal((long) item.getUnitPrice() * item.getQuantity())
                .thumbnail(item.getThumbnail())
                .metadata(item.getMetadata())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }

    private AddressResponse toAddressResponse(Address address) {
        if (address == null) {
            return null;
        }
        return AddressResponse.builder()
                .id(address.getId())
                .label(address.getLabel())
                .recipientName(address.getRecipientName())
                .phone(address.getPhone())
                .street(address.getStreet())
                .ward(address.getWard())
                .district(address.getDistrict())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .zipCode(address.getZipCode())
                .defaultShipping(address.isDefaultShipping())
                .defaultBilling(address.isDefaultBilling())
                .addressType(address.getAddressType())
                .createdAt(address.getCreatedAt())
                .updatedAt(address.getUpdatedAt())
                .build();
    }
}