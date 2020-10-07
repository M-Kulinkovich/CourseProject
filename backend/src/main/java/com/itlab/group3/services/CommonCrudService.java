package com.itlab.group3.services;

import com.itlab.group3.dao.model.AbstractEntity;

import java.util.Optional;

public interface CommonCrudService<T extends AbstractEntity> {
    T save(T t);

    Optional<T> findById(Long id);

    Iterable<T> findAll();

    void delete(T t);

}
