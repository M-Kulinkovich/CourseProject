package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Task;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import static com.itlab.group3.services.filter.ConditionSet.ConditionElement.elementOf;
import static com.itlab.group3.services.filter.Filter.Comparison.eq;

public class TaskFilter extends Filter<Task> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(
            elementOf("featureId", "feature.id", Long.class, Comparison.eq),
            elementOf("projectId", "feature.project.id", Long.class, Comparison.eq),
            elementOf("userId", null, Long.class, Comparison.eq)
    );

    private final UserAssignmentsRepository userAssignmentsRepository;

    public TaskFilter(String filterString, UserAssignmentsRepository userAssignmentsRepository) {
        super(filterString, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public TaskFilter(String externalName, Comparison comparison, String value, UserAssignmentsRepository userAssignmentsRepository) {
        super(externalName, comparison, value, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public static TaskFilter userIdFilter(Long userId, UserAssignmentsRepository userAssignmentsRepository) {
        return new TaskFilter("userId", eq, userId.toString(), userAssignmentsRepository);
    }

    @Override
    public Specification<Task> toSpecification() {
        if ("userId".equals(externalName)) {
            return UserProjectFilterUtil.createUserToProjectSpecification("feature.project.id", value, userAssignmentsRepository);
        }
        return super.toSpecification();
    }
}
