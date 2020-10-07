package com.itlab.group3.services.filter;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


public abstract class Filter<T> {

    private static final Map<Class<?>, Function<String, Object>> CONVERTERS = new HashMap<Class<?>, Function<String, Object>>() {{
        put(Integer.class, Integer::valueOf);
        put(Long.class, Long::valueOf);
        put(String.class, s -> s);
        put(Boolean.class, Boolean::valueOf);
        put(LocalDate.class, LocalDate::parse);
        put(LocalDateTime.class, LocalDateTime::parse);
    }};

    protected final String externalName;
    protected final ConditionSet conditionSet;
    protected final Comparison comparison;
    protected final String value;

    public Filter(String filterString, ConditionSet conditions) {
        String[] parts = new String(Base64.getDecoder().decode(filterString)).trim().split(",", 3);
        validateParts(parts);

        this.externalName = parts[0];
        this.comparison = Comparison.valueOf(parts[1]);
        this.value = clear(parts[2]);

        this.conditionSet = conditions;
    }

    public Filter(String externalName, Comparison comparison, String value, ConditionSet conditionSet) {
        this.externalName = externalName;
        this.comparison = comparison;
        this.value = value;
        this.conditionSet = conditionSet;
    }

    public static Path defaultResolvePath(Root root, String field) {
        if (field.contains(".")) {
            String[] temp = field.split("\\.");
            Join join = root.join(temp[0]);

            for (int i = 1; i < temp.length - 1; i++) {
                join = join.join(temp[i]);
            }
            return join.get(temp[temp.length - 1]);
        }
        return root.get(field);
    }

    private void validateField() {
        if (!conditionSet.containsKey(externalName) || !conditionSet.getCondition(externalName).getComparisons().contains(comparison)) {
            throw new InvalidFilterParameterException("Filter does not support");
        }
    }

    private void validateParts(String[] parts) {
        if (parts.length != 3) {
            throw new InvalidFilterParameterException("Filter string wrong format");
        }
    }

    public Specification<T> toSpecification() {
        validateField();
        return (root, criteriaQuery, criteriaBuilder) -> {
            String field = conditionSet.getField(externalName);
            Condition condition = conditionSet.getCondition(externalName);

            Path path = resolvePath(root, field);


            switch (comparison) {
                case eq:
                    return criteriaBuilder.equal(path, resolveValue(condition, value));
                case lt:
                    return criteriaBuilder.lessThan(path, (Comparable) resolveValue(condition, value));
                case gt:
                    return criteriaBuilder.greaterThan(path, (Comparable) resolveValue(condition, value));
                case gte:
                    return criteriaBuilder.greaterThanOrEqualTo(path, (Comparable) resolveValue(condition, value));
                case lte:
                    return criteriaBuilder.lessThanOrEqualTo(path, (Comparable) resolveValue(condition, value));
                case isn:
                    return criteriaBuilder.isNull(path);
                case nn:
                    return criteriaBuilder.isNotNull(path);
                case neq:
                    return criteriaBuilder.notEqual(path, resolveValue(condition, value));
                default:
                    throw new InvalidFilterParameterException("Unsupported comparison " + comparison);
            }
        };
    }

    private Object resolveValue(Condition condition, String value) {
        return CONVERTERS.get(condition.getType()).apply(value);
    }

    protected Path resolvePath(Root<T> root, String field) {
        return defaultResolvePath(root, field);
    }


    private String clear(String value) {
        try {
            return URLDecoder.decode(value, "UTF-8").replace("*", "").replace("%", "");
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }


    public enum Comparison {
        eq, lt, gt, lte, gte, isn, nn, neq
    }

    @Getter
    @RequiredArgsConstructor
    public static class Condition {
        private final Class<?> type;
        private final Set<Comparison> comparisons;

        public static Condition of(Class<?> type, Set<Comparison> comparisons) {
            return new Condition(type, comparisons);
        }
    }


}
