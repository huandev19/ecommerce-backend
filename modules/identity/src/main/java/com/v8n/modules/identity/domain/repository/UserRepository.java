package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.identity.domain.entity.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}