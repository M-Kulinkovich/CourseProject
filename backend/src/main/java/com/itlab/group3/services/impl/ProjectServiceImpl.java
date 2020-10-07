package com.itlab.group3.services.impl;

import com.itlab.group3.dao.FeatureRepository;
import com.itlab.group3.dao.ProjectRepository;
import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Project;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.ProjectService;
import com.itlab.group3.services.exceptions.DeleteException;
import com.itlab.group3.services.filter.filters.ProjectFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractCrudServiceImpl<Project> implements ProjectService {

    private final ProjectRepository projectRepository;

    private final UserAssignmentsRepository userAssignmentsRepository;

    private final FeatureRepository featureRepository;

    private final ReportRecordRepository reportRecordRepository;

    public ProjectServiceImpl(ProjectRepository repository, FeatureRepository featureRepository, ReportRecordRepository reportRecordRepository, UserAssignmentsRepository userAssignmentsRepository) {
        super(repository);
        projectRepository = repository;
        this.userAssignmentsRepository = userAssignmentsRepository;
        this.featureRepository = featureRepository;
        this.reportRecordRepository = reportRecordRepository;
    }

    @Override
    public JpaSpecificationExecutor<Project> getRepository() {
        return projectRepository;
    }

    @Override
    public void delete(Project project) {
        if (featureRepository.existsByProject(project)) {
            throw new DeleteException("Feature");
        }
        if (reportRecordRepository.existsByProject(project)) {
            throw new DeleteException("ReportRecord");
        }
        super.delete(project);
    }

    @Override
    public Specification<Project> getUserAdditionalSpecification(User user) {
        return ProjectFilter.userIdFilter(user.getId(), userAssignmentsRepository).toSpecification();
    }
}
