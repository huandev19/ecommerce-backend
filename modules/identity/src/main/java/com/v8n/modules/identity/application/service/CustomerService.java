package com.v8n.modules.identity.application.service;

import com.v8n.modules.core.application.dto.PageResponse;
import com.v8n.modules.core.application.exception.BusinessException;
import com.v8n.modules.core.application.exception.ErrorCode;
import com.v8n.modules.identity.application.dto.AdminCustomerResponse;
import com.v8n.modules.identity.application.dto.CustomerResponse;
import com.v8n.modules.identity.application.dto.UpdateProfileRequest;
import com.v8n.modules.identity.domain.entity.Customer;
import com.v8n.modules.identity.domain.repository.CustomerRepository;
import com.v8n.modules.identity.infrastructure.security.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByUserId(UUID userId) {
        log.info("customer id: {}", userId);
        Customer customer = customerRepository.findByIdNotDeleted(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));

        return mapToResponse(customer);
    }

    @Transactional
    public CustomerResponse updateProfile(UUID userId, UpdateProfileRequest request) {
        Customer customer = customerRepository.findByIdNotDeleted(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CUSTOMER_NOT_FOUND));

        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getPhone() != null) {
            customer.setPhone(request.getPhone());
        }
        if (request.getCompany() != null) {
            customer.setCompany(request.getCompany());
        }

        customer = customerRepository.save(customer);
        log.info("Customer profile updated for customer: {}", userId);

        return mapToResponse(customer);
    }

    /**
     * List customer users with optional email/name search and pagination.
     */
    @Transactional(readOnly = true)
    public PageResponse<AdminCustomerResponse> listCustomers(String query, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.searchByQuery(query, pageable);

        Page<AdminCustomerResponse> responsePage = customerPage.map(customer -> AdminCustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(customer.getFullName())
                .status(customer.getStatus())
                .emailVerified(customer.isEmailVerified())
                .hasAccount(customer.isHasAccount())
                .createdAt(customer.getCreatedAt())
                .lastLoginAt(customer.getLastLoginAt())
                .build());

        return PageResponse.from(responsePage);
    }

    /**
     * Force-revoke all tokens for a customer user.
     *
     * @param customerUserId the customer user whose tokens are being revoked
     * @param adminId        the admin who is performing the force-revoke
     */
    @Transactional
    public void revokeTokens(String customerUserId, String adminId) {
        tokenBlacklistService.revokeAllForUser(customerUserId, "customer", "ADMIN_FORCE_LOGOUT");
        log.info("Admin user {} force-revoked tokens for customer user {}", adminId, customerUserId);
    }

    private CustomerResponse mapToResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .email(customer.getEmail())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .fullName(customer.getFullName())
                .phone(customer.getPhone())
                .company(customer.getCompany())
                .avatarUrl(customer.getAvatarUrl())
                .status(customer.getStatus().name())
                .emailVerified(customer.isEmailVerified())
                .hasAccount(customer.isHasAccount())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .lastLoginAt(customer.getLastLoginAt())
                .build();
    }
}
