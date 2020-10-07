package com.itlab.group3.dao;

import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.dao.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends CommonCrudSpecificationRepository<Feature> {
    boolean existsByProject(Project project);
}
