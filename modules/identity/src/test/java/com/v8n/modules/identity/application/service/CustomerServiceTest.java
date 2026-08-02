package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.CustomerResponse;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private UUID customerId;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();

        mockCustomer = new Customer();
        mockCustomer.setId(customerId);
        mockCustomer.setEmail("test@example.com");
        mockCustomer.setStatus(Customer.CustomerStatus.ACTIVE);
        mockCustomer.setHasAccount(true);
    }

    @Test
    void testGetCustomerByUserId_success() {
        when(customerRepository.findByIdNotDeleted(customerId)).thenReturn(Optional.of(mockCustomer));

        CustomerResponse response = customerService.getCustomerByUserId(customerId);

        assertNotNull(response);
        assertEquals(mockCustomer.getId(), response.getId());
        assertEquals(mockCustomer.getEmail(), response.getEmail());
    }

    @Test
    void testGetCustomerByUserId_customerNotFound() {
        when(customerRepository.findByIdNotDeleted(customerId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
            customerService.getCustomerByUserId(customerId)
        );

        assertEquals(ErrorCode.CUSTOMER_NOT_FOUND, exception.getErrorCode());
    }
}
