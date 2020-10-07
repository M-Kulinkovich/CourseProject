package com.itlab.group3.dao;

import com.itlab.group3.dao.model.AbstractEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonCrudRepository<T extends AbstractEntity> extends CrudRepository<T, Long> {
}
