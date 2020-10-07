package com.itlab.group3.dao;

import com.itlab.group3.dao.model.AbstractEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommonCrudSpecificationRepository<T extends AbstractEntity> extends CommonCrudRepository<T>, JpaSpecificationExecutor<T> {
}
