package com.itlab.group3.dao;

import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.dao.model.Task;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends CommonCrudSpecificationRepository<Task> {
    boolean existsByFeature(Feature feature);
}
