package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AddressRequest;
import com.v8n.modules.identity.application.dto.AddressResponse;
import com.v8n.modules.identity.domain.entity.Address;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.AddressRepository;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

    private static final int MAX_ADDRESSES_PER_USER = 10;

    private final AddressRepository addressRepository;
    private final CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    public List<AddressResponse> getAddressesByUserId(UUID userId) {
        Customer customer = getCustomerByUserId(userId);
        return addressRepository.findAllByCustomerId(customer.getId()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AddressResponse getAddressById(UUID addressId, UUID userId) {
        Customer customer = getCustomerByUserId(userId);
        Address address = addressRepository.findByIdAndCustomerId(addressId, customer.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Address not found"));
        return mapToResponse(address);
    }

    @Transactional
    public AddressResponse createAddress(UUID userId, AddressRequest request) {
        Customer customer = getCustomerByUserId(userId);

        long addressCount = addressRepository.countByCustomerId(customer.getId());
        if (addressCount >= MAX_ADDRESSES_PER_USER) {
            throw new BusinessException(ErrorCode.INVALID_REQUEST,
                    "Maximum " + MAX_ADDRESSES_PER_USER + " addresses allowed per user");
        }

        if (Boolean.TRUE.equals(request.getDefaultShipping())) {
            clearDefaultShipping(customer.getId());
        }
        if (Boolean.TRUE.equals(request.getDefaultBilling())) {
            clearDefaultBilling(customer.getId());
        }

        Address address = new Address();
        address.setCustomer(customer);
        mapRequestToEntity(request, address);

        address = addressRepository.save(address);
        log.info("Address created for user {}: {}", userId, address.getId());
        return mapToResponse(address);
    }

    @Transactional
    public AddressResponse updateAddress(UUID addressId, UUID userId, AddressRequest request) {
        Customer customer = getCustomerByUserId(userId);
        Address address = addressRepository.findByIdAndCustomerId(addressId, customer.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Address not found"));

        if (Boolean.TRUE.equals(request.getDefaultShipping()) && !address.isDefaultShipping()) {
            clearDefaultShipping(customer.getId());
        }
        if (Boolean.TRUE.equals(request.getDefaultBilling()) && !address.isDefaultBilling()) {
            clearDefaultBilling(customer.getId());
        }

        mapRequestToEntity(request, address);
        address = addressRepository.save(address);
        log.info("Address updated for user {}: {}", userId, addressId);
        return mapToResponse(address);
    }

    @Transactional
    public void deleteAddress(UUID addressId, UUID userId) {
        Customer customer = getCustomerByUserId(userId);
        Address address = addressRepository.findByIdAndCustomerId(addressId, customer.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Address not found"));
        address.setDeletedAt(LocalDateTime.now());
        addressRepository.save(address);
        log.info("Address soft-deleted for user {}: {}", userId, addressId);
    }

    @Transactional
    public AddressResponse setDefaultShipping(UUID addressId, UUID userId) {
        Customer customer = getCustomerByUserId(userId);
        Address address = addressRepository.findByIdAndCustomerId(addressId, customer.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Address not found"));
        clearDefaultShipping(customer.getId());
        address.setDefaultShipping(true);
        address = addressRepository.save(address);
        log.info("Default shipping address set for user {}: {}", userId, addressId);
        return mapToResponse(address);
    }

    @Transactional
    public AddressResponse setDefaultBilling(UUID addressId, UUID userId) {
        Customer customer = getCustomerByUserId(userId);
        Address address = addressRepository.findByIdAndCustomerId(addressId, customer.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND, "Address not found"));
        clearDefaultBilling(customer.getId());
        address.setDefaultBilling(true);
        address = addressRepository.save(address);
        log.info("Default billing address set for user {}: {}", userId, addressId);
        return mapToResponse(address);
    }

    private void clearDefaultShipping(UUID customerId) {
        List<Address> defaultAddresses = addressRepository.findByCustomerIdAndDefaultShippingTrue(customerId);
        for (Address address : defaultAddresses) {
            address.setDefaultShipping(false);
        }
        addressRepository.saveAll(defaultAddresses);
    }

    private void clearDefaultBilling(UUID customerId) {
        addressRepository.findByCustomerIdAndDefaultBillingTrue(customerId)
                .ifPresent(addr -> {
                    addr.setDefaultBilling(false);
                    addressRepository.save(addr);
                });
    }

    private void mapRequestToEntity(AddressRequest request, Address address) {
        address.setLabel(request.getLabel());
        address.setRecipientName(request.getRecipientName());
        if (request.getRecipientName() != null) {
            String[] names = request.getRecipientName().trim().split("\\s+", 2);
            address.setFirstName(names[0]);
            address.setLastName(names.length > 1 ? names[1] : null);
        }
        address.setPhone(request.getPhone());
        address.setStreet(request.getStreet());
        address.setWard(request.getWard());
        address.setDistrict(request.getDistrict());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setZipCode(request.getZipCode());
        address.setAddressType(request.getAddressType());

        if (request.getDefaultShipping() != null) {
            address.setDefaultShipping(request.getDefaultShipping());
        }
        if (request.getDefaultBilling() != null) {
            address.setDefaultBilling(request.getDefaultBilling());
        }
    }

    private Customer getCustomerByUserId(UUID userId) {
        return customerRepository.findByIdNotDeleted(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));
    }

    private AddressResponse mapToResponse(Address address) {
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
