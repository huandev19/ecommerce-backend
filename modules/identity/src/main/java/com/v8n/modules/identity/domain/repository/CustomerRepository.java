package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.identity.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends BaseRepository<Customer, UUID> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    @org.springframework.data.jpa.repository.Query("SELECT c FROM Customer c WHERE " +
           "(:query IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "ORDER BY c.createdAt DESC")
    org.springframework.data.domain.Page<Customer> searchByQuery(
            @org.springframework.data.repository.query.Param("query") String query,
            org.springframework.data.domain.Pageable pageable);
}
