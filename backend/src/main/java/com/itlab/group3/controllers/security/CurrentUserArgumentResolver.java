package com.itlab.group3.controllers.security;

import com.itlab.group3.common.exception.ForbiddenException;
import com.itlab.group3.dao.model.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import com.itlab.group3.controllers.validation.CurrentUser;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Slf4j
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @NonNull
    private final Supplier<Optional<User>> currentUser;

    @Override
    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class)
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        final CurrentUser annotation = parameter.getParameterAnnotation(CurrentUser.class);

        return currentUser.get()
                .orElseGet(() -> {
                    if (annotation.required()) {
                        throw new ForbiddenException();
                    }
                    return null;
                });
    }
}
