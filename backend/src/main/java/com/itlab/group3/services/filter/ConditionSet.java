package com.itlab.group3.services.filter;

import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
public class ConditionSet {

    private final Map<String, ConditionElement> conditions;

    public static ConditionSet of(ConditionElement... conditionElements) {
        return new ConditionSet(StreamSupport.stream(Arrays.spliterator(conditionElements), false)
                .collect(Collectors.toMap(e -> e.externalName, e -> e)));
    }

    public String getField(String key) {
        return conditions.get(key).field;
    }

    public Filter.Condition getCondition(String key) {
        return conditions.get(key).condition;
    }

    public boolean containsKey(String key) {
        return conditions.containsKey(key);
    }

    @RequiredArgsConstructor
    public static class ConditionElement {

        private final String externalName;
        private final String field;
        private final Filter.Condition condition;

        public static ConditionElement elementOf(String externalName, String field, Filter.Condition comparison) {
            return new ConditionElement(externalName, field, comparison);
        }

        public static ConditionElement elementOf(String externalName, String field, Class<?> cls, Filter.Comparison... comparisons) {
            return elementOf(
                    externalName,
                    field,
                    Filter.Condition.of(
                            cls,
                            StreamSupport
                                    .stream(Arrays.spliterator(comparisons), false)
                                    .collect(Collectors.toSet()))

            );
        }
    }
}
