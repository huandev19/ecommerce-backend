package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.identity.domain.entity.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends BaseRepository<Address, UUID> {

    List<Address> findAllByCustomerId(UUID customerId);

    Optional<Address> findByIdAndCustomerId(UUID id, UUID customerId);

    Optional<Address> findByCustomerIdAndDefaultShippingTrue(UUID customerId);

    Optional<Address> findByCustomerIdAndDefaultBillingTrue(UUID customerId);

    boolean existsByIdAndCustomerId(UUID id, UUID customerId);

    long countByCustomerId(UUID customerId);
}
