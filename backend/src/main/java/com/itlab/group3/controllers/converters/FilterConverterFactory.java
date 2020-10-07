package com.itlab.group3.controllers.converters;

import com.itlab.group3.dao.UserAssignmentsRepository;
import com.itlab.group3.services.filter.Filter;
import com.itlab.group3.services.filter.filters.DetailedTaskFilter;
import com.itlab.group3.services.filter.filters.FeatureFilter;
import com.itlab.group3.services.filter.filters.ProjectFilter;
import com.itlab.group3.services.filter.filters.TaskFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor
public class FilterConverterFactory implements ConverterFactory<String, Filter<?>> {

    private final UserAssignmentsRepository userAssignmentsRepository;

    @Override
    public <T extends Filter<?>> Converter<String, T> getConverter(Class<T> aClass) {
        return new FilterConverter<>(aClass, userAssignmentsRepository);
    }

    @RequiredArgsConstructor
    public static class FilterConverter<T extends Filter<?>> implements Converter<String, T> {

        private final Class<T> filterType;

        private final UserAssignmentsRepository userAssignmentsRepository;

        @Override
        public T convert(String s) {
            if (filterType.equals(ProjectFilter.class)) {
                return (T) new ProjectFilter(s, userAssignmentsRepository);
            } else if (filterType.equals(FeatureFilter.class)) {
                return (T) new FeatureFilter(s, userAssignmentsRepository);
            } else if (filterType.equals(TaskFilter.class)) {
                return (T) new TaskFilter(s, userAssignmentsRepository);
            } else if (filterType.equals(DetailedTaskFilter.class)) {
                return (T) new DetailedTaskFilter(s, userAssignmentsRepository);
            }
            try {
                return filterType.getDeclaredConstructor(String.class).newInstance(s);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
