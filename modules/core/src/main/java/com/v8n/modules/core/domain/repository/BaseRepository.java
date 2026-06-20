package com.v8n.modules.core.domain.repository;

import com.v8n.modules.core.domain.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;
import java.util.UUID;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    default Optional<T> findByIdNotDeleted(UUID id) {
        // findById does NOT filter soft-deletes by default.
        // Concrete repositories must override this with @Query("WHERE deletedAt IS NULL").
        // Fallback: fetch and check null.
        if (id == null) {
            return Optional.empty();
        }
        Optional<T> result = findById((ID) id);
        if (result.isPresent() && result.get() instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) result.get();
            if (entity.getDeletedAt() != null) {
                return Optional.empty();
            }
        }
        return result;
    }

    default boolean existsByIdNotDeleted(UUID id) {
        if (id == null) {
            return false;
        }
        Optional<T> result = findById((ID) id);
        if (result.isPresent() && result.get() instanceof BaseEntity) {
            BaseEntity entity = (BaseEntity) result.get();
            if (entity.getDeletedAt() != null) {
                return false;
            }
        }
        return result.isPresent();
    }
}
