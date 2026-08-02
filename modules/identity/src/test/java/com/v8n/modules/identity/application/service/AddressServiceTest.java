package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AddressRequest;
import com.v8n.modules.identity.domain.entity.Address;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.AddressRepository;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AddressService addressService;

    private UUID customerId;
    private UUID addressId;
    private Customer mockCustomer;
    private Address mockAddress;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        addressId = UUID.randomUUID();

        mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setEmail("test@example.com");

        mockAddress = new Address();
        mockAddress.setId(addressId);
        mockAddress.setCustomer(mockCustomer);
    }

    @Test
    void testDeleteAddress_softDelete_setsDeletedAt() {
        when(customerRepository.findByIdNotDeleted(customerId)).thenReturn(Optional.of(mockCustomer));
        when(addressRepository.findByIdAndCustomerId(addressId, customerId)).thenReturn(Optional.of(mockAddress));

        assertNull(mockAddress.getDeletedAt());

        addressService.deleteAddress(addressId, customerId);

        assertNotNull(mockAddress.getDeletedAt());
        verify(addressRepository).save(mockAddress);
    }

    @Test
    void testDeleteAddress_addressNotFound_throwsException() {
        when(customerRepository.findByIdNotDeleted(customerId)).thenReturn(Optional.of(mockCustomer));
        when(addressRepository.findByIdAndCustomerId(addressId, customerId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
            addressService.deleteAddress(addressId, customerId)
        );

        assertEquals(ErrorCode.RESOURCE_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testCreateAddress_exceedsLimit_throwsException() {
        when(customerRepository.findByIdNotDeleted(customerId)).thenReturn(Optional.of(mockCustomer));
        when(addressRepository.countByCustomerId(customerId)).thenReturn(10L); // MAX_ADDRESSES_PER_USER

        AddressRequest request = new AddressRequest();

        BusinessException exception = assertThrows(BusinessException.class, () ->
            addressService.createAddress(customerId, request)
        );

        assertEquals(ErrorCode.INVALID_REQUEST, exception.getErrorCode());
        assertTrue(exception.getMessage().contains("Maximum 10 addresses allowed"));
    }
}
