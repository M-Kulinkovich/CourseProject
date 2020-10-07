package com.itlab.group3.config;

import com.itlab.group3.controllers.converters.FilterConverterFactory;
import com.itlab.group3.controllers.converters.ModelConverterFactory;
import com.itlab.group3.controllers.security.CurrentUserArgumentResolver;
import com.itlab.group3.controllers.security.UserAgent;
import com.itlab.group3.dao.UserAssignmentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ConverterConfiguration implements WebMvcConfigurer {

    private final UserAssignmentsRepository userAssignmentsRepository;

    private final UserAgent userAgent;

    @NonNull
    private final EntityManager entityManager;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new FilterConverterFactory(userAssignmentsRepository));
        registry.addConverterFactory(new ModelConverterFactory(entityManager));
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new CurrentUserArgumentResolver(userAgent::getUser));
    }
}
