package com.itlab.group3.services.common;

import com.itlab.group3.dao.model.AbstractEntity;
import com.itlab.group3.services.filter.Filter;
import com.itlab.group3.services.filter.FilterUtil;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import java.util.List;

public interface FindAllFilterService<T extends AbstractEntity> extends CommonSpecificationRepositoryService<T> {

    default List<T> findAll(List<? extends Filter<T>> filters) {
        return getRepository().findAll(FilterUtil.applyFilters(filters));
    }

    default List<T> findAll(List<? extends Filter<T>> filters, @Nullable List<Specification<T>> additional) {
        return getRepository().findAll(FilterUtil.applyFilters(filters, additional));
    }

}
