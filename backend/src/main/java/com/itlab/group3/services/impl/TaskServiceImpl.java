package com.itlab.group3.services.impl;

import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.TaskRepository;
import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Task;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.TaskService;
import com.itlab.group3.services.exceptions.DeleteException;
import com.itlab.group3.services.filter.filters.TaskFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl extends AbstractCrudServiceImpl<Task> implements TaskService {

    @NonNull
    private final TaskRepository taskRepository;

    private final UserAssignmentsRepository userAssignmentsRepository;

    @NonNull
    private final ReportRecordRepository reportRecordRepository;

    public TaskServiceImpl(TaskRepository repository, ReportRecordRepository reportRecordRepository, UserAssignmentsRepository userAssignmentsRepository) {
        super(repository);
        this.taskRepository = repository;
        this.userAssignmentsRepository = userAssignmentsRepository;
        this.reportRecordRepository = reportRecordRepository;
    }

    @Override
    public JpaSpecificationExecutor<Task> getRepository() {
        return taskRepository;
    }

    @Override
    public Specification<Task> getUserAdditionalSpecification(User user) {
        return TaskFilter.userIdFilter(user.getId(), userAssignmentsRepository).toSpecification();
    }

    @Override
    public void delete(Task task) {
        if (reportRecordRepository.existsByTask(task)) {
            throw new DeleteException("ReportRecord");
        }
        super.delete(task);
    }
}
