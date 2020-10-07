package com.itlab.group3.services.filter.filters;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.dao.model.AbstractEntity;
import com.itlab.group3.dao.model.UserAssignments;
import com.itlab.group3.services.filter.Filter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserProjectFilterUtil {

    public static <T> Specification<T> createUserToProjectSpecification(String projectIdPath, String value, UserAssignmentsRepository userAssignmentsRepository) {
        UserAssignments userAssignments = userAssignmentsRepository.findByUserId(Long.valueOf(value)).orElse(null);
        List<Long> ids;
        if (userAssignments == null) {
            ids = Collections.singletonList(-1L);
        } else {
            ids = userAssignments.getProjects().stream().map(AbstractEntity::getId).collect(Collectors.toList());
        }
        return (Specification<T>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder
                        .in(Filter.defaultResolvePath(root, projectIdPath))
                        .value(ids);
    }

}
