package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.CustomerResponse;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.entity.User;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import com.v8n.modules.identity.domain.repository.UserRepository;
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

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomerService customerService;

    private UUID userId;
    private User mockUser;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        mockUser = new User();
        mockUser.setId(userId);
        mockUser.setEmail("test@example.com");

        mockCustomer = new Customer();
        mockCustomer.setId(UUID.randomUUID());
        mockCustomer.setUser(mockUser);
        mockCustomer.setEmail("test@example.com");
    }

    @Test
    void testGetCustomerByUserId_success() {
        when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.of(mockUser));
        when(customerRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.of(mockCustomer));

        CustomerResponse response = customerService.getCustomerByUserId(userId);

        assertNotNull(response);
        assertEquals(mockCustomer.getId(), response.getId());
        assertEquals(userId, response.getUserId());
    }

    @Test
    void testGetCustomerByUserId_userNotFound() {
        when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> 
            customerService.getCustomerByUserId(userId)
        );

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }

    @Test
    void testGetCustomerByUserId_customerNotFound() {
        when(userRepository.findByIdNotDeleted(userId)).thenReturn(Optional.of(mockUser));
        when(customerRepository.findByEmail(mockUser.getEmail())).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> 
            customerService.getCustomerByUserId(userId)
        );

        assertEquals(ErrorCode.CUSTOMER_NOT_FOUND, exception.getErrorCode());
    }
}
