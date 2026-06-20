package com.v8n.modules.cart.application.service;

import com.v8n.modules.cart.application.dto.AddLineItemRequest;
import com.v8n.modules.cart.application.dto.CartResponse;
import com.v8n.modules.cart.application.dto.CreateCartRequest;
import com.v8n.modules.cart.application.dto.UpdateLineItemRequest;
import com.v8n.modules.cart.domain.entity.Cart;
import com.v8n.modules.cart.domain.entity.LineItem;
import com.v8n.modules.cart.domain.repository.CartRepository;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.domain.entity.Address;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import com.v8n.modules.catalog.domain.entity.Region;
import com.v8n.modules.catalog.domain.entity.ProductVariant;
import com.v8n.modules.catalog.domain.repository.ProductVariantRepository;
import com.v8n.modules.catalog.domain.repository.RegionRepository;
import com.v8n.modules.identity.application.dto.AddressRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerRepository customerRepository;
    private final ProductVariantRepository variantRepository;
    private final RegionRepository regionRepository;
    private final CartMapper cartMapper;

    // ===== Query Methods =====

    @Transactional(readOnly = true)
    public CartResponse getCartById(UUID cartId) {
        Cart cart = findCartById(cartId);
        return cartMapper.toResponse(cart);
    }

    @Transactional(readOnly = true)
    public CartResponse getActiveCartByCustomerId(UUID customerId) {
        Cart cart = cartRepository.findActiveCartByCustomerId(customerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
        return cartMapper.toResponse(cart);
    }

    // ===== Mutation Methods =====

    @Transactional
    public CartResponse createCart(CreateCartRequest request) {
        Cart cart = new Cart();

        // Set region
        if (request.getRegionId() != null) {
            Region region = regionRepository.findByIdNotDeleted(request.getRegionId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.REGION_NOT_FOUND));
            cart.setRegion(region);
        }

        // Set customer if provided
        if (request.getCustomerId() != null) {
            Customer customer = customerRepository.findByIdNotDeleted(request.getCustomerId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));
            cart.setCustomer(customer);
            cart.setEmail(customer.getEmail());
        }

        // Set currency code from region or request
        if (request.getCurrencyCode() != null) {
            cart.setCurrencyCode(request.getCurrencyCode());
        } else if (cart.getRegion() != null) {
            cart.setCurrencyCode(cart.getRegion().getCurrencyCode());
        }

        cart = cartRepository.save(cart);
        log.info("Cart created with id: {}", cart.getId());

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse addLineItem(UUID cartId, AddLineItemRequest request) {
        Cart cart = findCartById(cartId);

        if (cart.isCompleted()) {
            throw new BusinessException(ErrorCode.CART_EXPIRED);
        }

        // Validate variant
        ProductVariant variant = variantRepository.findByIdNotDeleted(request.getVariantId())
                .orElseThrow(() -> new BusinessException(ErrorCode.VARIANT_NOT_FOUND));

        // Validate quantity
        if (request.getQuantity() <= 0) {
            throw new BusinessException(ErrorCode.INVALID_QUANTITY);
        }

        String title = request.getTitle() != null ? request.getTitle() : variant.getProduct().getTitle();
        cart.addItem(variant, request.getQuantity(), request.getUnitPrice(), title, request.getThumbnail());

        cart = cartRepository.save(cart);
        log.info("Line item added to cart {}: variant={}, quantity={}", cartId, request.getVariantId(), request.getQuantity());

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse updateLineItemQuantity(UUID cartId, UUID lineItemId, UpdateLineItemRequest request) {
        Cart cart = findCartById(cartId);

        if (cart.isCompleted()) {
            throw new BusinessException(ErrorCode.CART_EXPIRED);
        }

        LineItem lineItem = cart.getLineItems().stream()
                .filter(item -> item.getId().equals(lineItemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.LINE_ITEM_NOT_FOUND));

        if (request.getQuantity() <= 0) {
            throw new BusinessException(ErrorCode.INVALID_QUANTITY);
        }

        lineItem.setQuantity(request.getQuantity());
        cart = cartRepository.save(cart);
        log.info("Line item {} quantity updated to {} in cart {}", lineItemId, request.getQuantity(), cartId);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse removeLineItem(UUID cartId, UUID lineItemId) {
        Cart cart = findCartById(cartId);

        if (cart.isCompleted()) {
            throw new BusinessException(ErrorCode.CART_EXPIRED);
        }

        LineItem lineItem = cart.getLineItems().stream()
                .filter(item -> item.getId().equals(lineItemId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.LINE_ITEM_NOT_FOUND));

        cart.removeItem(lineItem);
        cart = cartRepository.save(cart);
        log.info("Line item {} removed from cart {}", lineItemId, cartId);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse clearCart(UUID cartId) {
        Cart cart = findCartById(cartId);

        if (cart.isCompleted()) {
            throw new BusinessException(ErrorCode.CART_EXPIRED);
        }

        cart.clearItems();
        cart = cartRepository.save(cart);
        log.info("Cart {} cleared", cartId);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse updateShippingAddress(UUID cartId, AddressRequest addressRequest) {
        Cart cart = findCartById(cartId);

        if (cart.isCompleted()) {
            throw new BusinessException(ErrorCode.CART_EXPIRED);
        }

        Address address = new Address();
        address.setLabel(addressRequest.getLabel());
        address.setRecipientName(addressRequest.getRecipientName());
        address.setPhone(addressRequest.getPhone());
        address.setStreet(addressRequest.getStreet());
        address.setWard(addressRequest.getWard());
        address.setDistrict(addressRequest.getDistrict());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setCountry(addressRequest.getCountry());
        address.setZipCode(addressRequest.getZipCode());

        cart.setShippingAddress(address);
        cart = cartRepository.save(cart);
        log.info("Shipping address updated for cart {}", cartId);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse updateBillingAddress(UUID cartId, AddressRequest addressRequest) {
        Cart cart = findCartById(cartId);

        if (cart.isCompleted()) {
            throw new BusinessException(ErrorCode.CART_EXPIRED);
        }

        Address address = new Address();
        address.setLabel(addressRequest.getLabel());
        address.setRecipientName(addressRequest.getRecipientName());
        address.setPhone(addressRequest.getPhone());
        address.setStreet(addressRequest.getStreet());
        address.setWard(addressRequest.getWard());
        address.setDistrict(addressRequest.getDistrict());
        address.setCity(addressRequest.getCity());
        address.setState(addressRequest.getState());
        address.setCountry(addressRequest.getCountry());
        address.setZipCode(addressRequest.getZipCode());

        cart.setBillingAddress(address);
        cart = cartRepository.save(cart);
        log.info("Billing address updated for cart {}", cartId);

        return cartMapper.toResponse(cart);
    }

    @Transactional
    public void deleteCart(UUID cartId) {
        Cart cart = findCartById(cartId);
        cart.setDeletedAt(java.time.LocalDateTime.now());
        cartRepository.save(cart);
        log.info("Cart {} soft-deleted", cartId);
    }

    // ===== Internal Helpers =====

    private Cart findCartById(UUID cartId) {
        return cartRepository.findByIdNotDeleted(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
    }
}