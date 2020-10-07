package com.itlab.group3.services.filter;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class FilterUtil {

    public static <T> Specification<T> applyFilters(List<? extends Filter<T>> filters, List<Specification<T>> additional) {
        Specification<T> result = null;
        for (Specification<T> specification : additional) {
            if (specification != null) {
                if (result == null) {
                    result = Specification.where(specification);
                } else {
                    result = result.and(specification);
                }
            }
        }

        return applyFilters(filters, result);
    }

    public static <T> Specification<T> applyFilters(List<? extends Filter<T>> filters, Specification<T> additional) {
        Specification<T> result = applyFilters(filters);
        if (additional == null) {
            return result;
        }
        if (result == null) {
            return Specification.where(additional);
        }
        return result.and(additional);
    }

    public static <T> Specification<T> applyFilters(List<? extends Filter<T>> filters) {
        if (filters == null) {
            return null;
        }
        Specification<T> result = null;
        for (Filter<T> filter : filters) {
            if (result == null) {
                result = Specification.where(filter.toSpecification());
            } else {
                result = result.and(filter.toSpecification());
            }
        }
        return result;
    }
}
