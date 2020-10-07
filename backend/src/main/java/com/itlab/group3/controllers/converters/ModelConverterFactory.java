package com.itlab.group3.controllers.converters;

import com.itlab.group3.dao.model.AbstractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.lang.NonNull;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class ModelConverterFactory implements ConverterFactory<String, AbstractEntity> {

    @NonNull
    private final EntityManager entityManager;

    @Override
    public <T extends AbstractEntity> Converter<String, T> getConverter(Class<T> aClass) {
        return new ModelConverter<T>(aClass, entityManager);
    }

    @RequiredArgsConstructor
    private static class ModelConverter<T extends AbstractEntity> implements Converter<String, T> {

        @NonNull
        private final Class<T> type;

        @NonNull
        private final EntityManager entityManager;

        @Override
        public T convert(String id) {
            T t = entityManager.find(type, Long.valueOf(id));
            if (t == null) {
                throw new ElementNotFoundException();
            }
            return t;
        }
    }

    public static class ElementNotFoundException extends RuntimeException {
        ElementNotFoundException() {
            super("element not found");
        }
    }

}
