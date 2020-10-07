package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = TaskExist.Validator.class)
public @interface TaskExist {

    String message() default "{task doesnt exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<TaskExist, Long> {

        @Autowired
        private TaskRepository taskRepository;

        @Override
        public void initialize(TaskExist constraintAnnotation) {

        }

        @Override
        public boolean isValid(Long id, ConstraintValidatorContext context) {
            return id == null || taskRepository.existsById(id);
        }
    }
}
