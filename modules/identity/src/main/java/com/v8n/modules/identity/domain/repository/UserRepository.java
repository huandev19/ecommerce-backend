package com.v8n.modules.identity.domain.repository;

import com.v8n.modules.core.domain.repository.BaseRepository;
import com.v8n.modules.identity.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends BaseRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
           "(:query IS NULL OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "ORDER BY u.createdAt DESC")
    Page<User> searchByQuery(@Param("query") String query, Pageable pageable);
}
