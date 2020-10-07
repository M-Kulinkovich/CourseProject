package com.itlab.group3.controllers.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface CurrentUser {

    boolean required() default false;

}
