package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AuthResponse;
import com.v8n.modules.identity.application.dto.CustomerResponse;
import com.v8n.modules.identity.application.dto.LoginRequest;
import com.v8n.modules.identity.application.dto.RegisterRequest;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import com.v8n.modules.identity.infrastructure.security.JwtTokenProvider;
import com.v8n.modules.identity.infrastructure.security.TokenBlacklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private TokenBlacklistService tokenBlacklistService;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;
    private LoginRequest loginRequest;
    private Customer mockCustomer;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setFirstName("Test");
        registerRequest.setLastName("User");
        registerRequest.setPhone("0123456789");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        mockCustomer = new Customer();
        mockCustomer.setId(UUID.randomUUID());
        mockCustomer.setEmail("test@example.com");
        mockCustomer.setFirstName("Test");
        mockCustomer.setLastName("User");
        mockCustomer.setHasAccount(true);
        mockCustomer.setStatus(Customer.CustomerStatus.ACTIVE);
    }

    @Test
    void testRegister_success() {
        when(customerRepository.existsByEmail(registerRequest.getEmail())).thenReturn(false);
        when(passwordEncoder.encode(registerRequest.getPassword())).thenReturn("hashed_password");
        when(customerRepository.save(any(Customer.class))).thenAnswer(inv -> {
            Customer c = inv.getArgument(0);
            c.setId(UUID.randomUUID());
            return c;
        });
        when(jwtTokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access_token");
        when(jwtTokenProvider.generateRefreshToken(any(java.util.UUID.class))).thenReturn("refresh_token");
        when(jwtTokenProvider.getAccessTokenExpiration()).thenReturn(3600000L);

        AuthResponse response = authService.register(registerRequest);

        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertEquals("refresh_token", response.getRefreshToken());
        assertEquals("Bearer", response.getTokenType());
        assertNotNull(response.getUser());
        assertEquals("test@example.com", response.getUser().getEmail());
        assertEquals("ACTIVE", response.getUser().getStatus());
        assertTrue(response.getUser().isHasAccount());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testRegister_emailAlreadyExists() {
        when(customerRepository.existsByEmail(registerRequest.getEmail())).thenReturn(true);

        BusinessException exception = assertThrows(BusinessException.class, () ->
            authService.register(registerRequest)
        );

        assertEquals(ErrorCode.EMAIL_ALREADY_EXISTS, exception.getErrorCode());
    }

    @Test
    void testLogin_success() {
        mockCustomer.setPasswordHash("hashed_password");
        when(customerRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockCustomer));
        when(passwordEncoder.matches(loginRequest.getPassword(), "hashed_password")).thenReturn(true);
        when(jwtTokenProvider.generateAccessToken(any(), any(), any())).thenReturn("access_token");
        when(jwtTokenProvider.generateRefreshToken(any(java.util.UUID.class))).thenReturn("refresh_token");
        when(jwtTokenProvider.getAccessTokenExpiration()).thenReturn(3600000L);

        AuthResponse response = authService.login(loginRequest, null);

        assertNotNull(response);
        assertEquals("access_token", response.getAccessToken());
        assertNotNull(response.getUser());
        assertNotNull(mockCustomer.getLastLoginAt());
    }

    @Test
    void testLogin_bannedAccount() {
        mockCustomer.setStatus(Customer.CustomerStatus.BANNED);
        when(customerRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockCustomer));

        BusinessException exception = assertThrows(BusinessException.class, () ->
            authService.login(loginRequest, null)
        );

        assertEquals(ErrorCode.INVALID_CREDENTIALS, exception.getErrorCode());
    }

    @Test
    void testLogin_noAccount() {
        mockCustomer.setHasAccount(false);
        when(customerRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(mockCustomer));

        BusinessException exception = assertThrows(BusinessException.class, () ->
            authService.login(loginRequest, null)
        );

        assertEquals(ErrorCode.INVALID_CREDENTIALS, exception.getErrorCode());
    }

    @Test
    void testGetCurrentUser_success() {
        when(customerRepository.findByIdNotDeleted(mockCustomer.getId())).thenReturn(Optional.of(mockCustomer));

        CustomerResponse response = authService.getCurrentUser(mockCustomer.getId().toString());

        assertNotNull(response);
        assertEquals(mockCustomer.getEmail(), response.getEmail());
    }
}
