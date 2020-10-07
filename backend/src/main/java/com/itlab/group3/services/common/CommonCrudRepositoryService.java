package com.itlab.group3.services.common;

import com.itlab.group3.dao.CommonCrudRepository;
import com.itlab.group3.dao.model.AbstractEntity;

public interface CommonCrudRepositoryService<T extends AbstractEntity>
        extends CommonRepositoryService<CommonCrudRepository<T>> {
}
