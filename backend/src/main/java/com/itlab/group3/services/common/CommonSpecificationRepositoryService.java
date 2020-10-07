package com.itlab.group3.services.common;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CommonSpecificationRepositoryService<T> extends CommonRepositoryService<JpaSpecificationExecutor<T>> {
}
