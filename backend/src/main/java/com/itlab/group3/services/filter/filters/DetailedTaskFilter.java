package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.DetailedTask;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import static com.itlab.group3.services.filter.ConditionSet.ConditionElement.elementOf;

public class DetailedTaskFilter extends Filter<DetailedTask> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(
            elementOf("taskId", "task.id", Long.class, Comparison.eq),
            elementOf("featureId", "task.feature.id", Long.class, Comparison.eq),
            elementOf("projectId", "task.feature.project.id", Long.class, Comparison.eq),
            elementOf("userId", null, Long.class, Comparison.eq)
    );

    private final UserAssignmentsRepository userAssignmentsRepository;

    public DetailedTaskFilter(String filterString, UserAssignmentsRepository userAssignmentsRepository) {
        super(filterString, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public DetailedTaskFilter(String externalName, Comparison comparison, String value, UserAssignmentsRepository userAssignmentsRepository) {
        super(externalName, comparison, value, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }


    public static Filter<DetailedTask> userIdFilter(Long id, UserAssignmentsRepository userAssignmentsRepository) {
        return new DetailedTaskFilter("userId", Comparison.eq, id.toString(), userAssignmentsRepository);
    }

    @Override
    public Specification<DetailedTask> toSpecification() {
        if ("userId".equals(externalName)) {
            return UserProjectFilterUtil.createUserToProjectSpecification("task.feature.project.id", value, userAssignmentsRepository);
        }
        return super.toSpecification();
    }
}
