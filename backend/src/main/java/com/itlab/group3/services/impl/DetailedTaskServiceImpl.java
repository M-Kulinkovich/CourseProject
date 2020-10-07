package com.itlab.group3.services.impl;

import com.itlab.group3.dao.DetailedTaskRepository;
import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.DetailedTask;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.DetailedTaskService;
import com.itlab.group3.services.exceptions.DeleteException;
import com.itlab.group3.services.filter.filters.DetailedTaskFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Service;

@Service
public class DetailedTaskServiceImpl extends AbstractCrudServiceImpl<DetailedTask> implements DetailedTaskService {

    private final DetailedTaskRepository detailedTaskRepository;

    private final UserAssignmentsRepository userAssignmentsRepository;

    private final ReportRecordRepository reportRecordRepository;

    public DetailedTaskServiceImpl(DetailedTaskRepository repository, UserAssignmentsRepository userAssignmentsRepository, ReportRecordRepository reportRecordRepository) {
        super(repository);
        detailedTaskRepository = repository;
        this.userAssignmentsRepository = userAssignmentsRepository;
        this.reportRecordRepository = reportRecordRepository;
    }

    @Override
    public JpaSpecificationExecutor<DetailedTask> getRepository() {
        return detailedTaskRepository;
    }

    @Override
    public Specification<DetailedTask> getUserAdditionalSpecification(User user) {
        return DetailedTaskFilter.userIdFilter(user.getId(), userAssignmentsRepository).toSpecification();
    }

    @Override
    public void delete(DetailedTask detailedTask) {
        if (reportRecordRepository.existsByDetailedTask(detailedTask)) {
            throw new DeleteException("DetailedTask");
        }
        super.delete(detailedTask);
    }
}
