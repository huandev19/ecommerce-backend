package com.v8n.modules.core.application.service;

import com.v8n.modules.core.domain.repository.BaseRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class BaseService<T, R extends BaseRepository<T, UUID>> {

    protected final R repository;

    public T save(T entity) {
        return repository.save(entity);
    }

    public Optional<T> findById(UUID id) {
        return repository.findByIdNotDeleted(id);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
