package com.itlab.group3.services.common;

import com.itlab.group3.dao.model.AbstractEntity;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.filter.Filter;
import com.itlab.group3.services.filter.FilterUtil;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface FindAllFilterUserRoleSeparationService<T extends AbstractEntity> extends CommonSpecificationRepositoryService<T> {

    default Specification<T> getAdminAdditionalSpecification(User user) {
        return null;
    }

    default Specification<T> getUserAdditionalSpecification(User user) {
        return null;
    }

    default List<T> findAll(List<? extends Filter<T>> filters, User user) {
        Specification<T> specification;
        if (user.isAdmin()) {
            specification = getAdminAdditionalSpecification(user);
        } else {
            specification = getUserAdditionalSpecification(user);
        }
        return getRepository().findAll(FilterUtil.applyFilters(filters, specification));
    }
}
