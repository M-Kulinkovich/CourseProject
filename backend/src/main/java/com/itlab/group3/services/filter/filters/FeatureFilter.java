package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.services.filter.ConditionSet;
import com.itlab.group3.services.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import static com.itlab.group3.services.filter.ConditionSet.ConditionElement.elementOf;
import static com.itlab.group3.services.filter.Filter.Comparison.eq;

public class FeatureFilter extends Filter<Feature> {

    private static final ConditionSet CONDITIONS = ConditionSet.of(
            elementOf("projectId", "project.id", Long.class, Comparison.eq),
            elementOf("taskId", "tasks.id", Long.class, Comparison.eq),
            elementOf("userId", null, Long.class, Comparison.eq)
    );

    private final UserAssignmentsRepository userAssignmentsRepository;


    public FeatureFilter(String filterString, UserAssignmentsRepository userAssignmentsRepository) {
        super(filterString, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public FeatureFilter(String externalName, Comparison comparison, String value, UserAssignmentsRepository userAssignmentsRepository) {
        super(externalName, comparison, value, CONDITIONS);
        this.userAssignmentsRepository = userAssignmentsRepository;
    }

    public static FeatureFilter userIdFilter(Long userId, UserAssignmentsRepository userAssignmentsRepository) {
        return new FeatureFilter("userId", eq, userId.toString(), userAssignmentsRepository);
    }

    @Override
    public Specification<Feature> toSpecification() {
        if ("userId".equals(externalName)) {
            return UserProjectFilterUtil.createUserToProjectSpecification("project.id", value, userAssignmentsRepository);
        }
        return super.toSpecification();
    }
}
