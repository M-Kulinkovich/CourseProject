package com.itlab.group3.services.impl;

import com.itlab.group3.dao.FeatureRepository;
import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.TaskRepository;
import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.FeatureService;
import com.itlab.group3.services.exceptions.DeleteException;
import com.itlab.group3.services.filter.filters.FeatureFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class FeatureServiceImpl extends AbstractCrudServiceImpl<Feature> implements FeatureService {

    @NonNull
    private final FeatureRepository featureRepository;

    private final UserAssignmentsRepository userAssignmentsRepository;

    private final TaskRepository taskRepository;

    private final ReportRecordRepository reportRecordRepository;

    public FeatureServiceImpl(FeatureRepository repository, UserAssignmentsRepository userAssignmentsRepository, TaskRepository taskRepository, ReportRecordRepository reportRecordRepository) {
        super(repository);
        featureRepository = repository;
        this.userAssignmentsRepository = userAssignmentsRepository;
        this.taskRepository = taskRepository;
        this.reportRecordRepository = reportRecordRepository;
    }

    @Override
    public JpaSpecificationExecutor<Feature> getRepository() {
        return featureRepository;
    }

    @Override
    public Specification<Feature> getUserAdditionalSpecification(User user) {
        return FeatureFilter.userIdFilter(user.getId(), userAssignmentsRepository).toSpecification();
    }

    @Override
    public void delete(Feature feature) {
        if (taskRepository.existsByFeature(feature)) {
            throw new DeleteException("Task");
        }
        if (reportRecordRepository.existsByFeature(feature)) {
            throw new DeleteException("ReportRecord");
        }
        super.delete(feature);
    }
}
