package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Project;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import static com.itlab.group3.services.filter.ConditionSet.ConditionElement.elementOf;
import static com.itlab.group3.services.filter.Filter.Comparison.eq;

public class ProjectFilter extends Filter<Project> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(
            elementOf("taskId", "features.tasks.id", Long.class, eq),
            elementOf("featureId", "features.id", Long.class, eq),
            elementOf("reportId", "reportRecords.report.id", Long.class, eq),
            elementOf("userId", null, Long.class, eq)
    );

    private final UserAssignmentsRepository userAssignmentsRepository;

    public ProjectFilter(String filterString, UserAssignmentsRepository userAssignmentsRepository) {
        super(filterString, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public ProjectFilter(String externalName, Comparison comparison, String value, UserAssignmentsRepository userAssignmentsRepository) {
        super(externalName, comparison, value, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public static ProjectFilter userIdFilter(Long userId, UserAssignmentsRepository userAssignmentsRepository) {
        return new ProjectFilter("userId", eq, userId.toString(), userAssignmentsRepository);
    }

    @Override
    public Specification<Project> toSpecification() {
        if ("userId".equals(externalName)) {
            return UserProjectFilterUtil.createUserToProjectSpecification("id", value, userAssignmentsRepository);
        }
        return super.toSpecification();
    }

}
