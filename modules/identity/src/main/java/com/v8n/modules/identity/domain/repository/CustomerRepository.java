package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.identity.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends BaseRepository<Customer, UUID> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);
}
