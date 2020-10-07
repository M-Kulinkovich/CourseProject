package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = ProjectsExist.Validator.class)
public @interface ProjectsExist {

    String message() default "{project doesnt exist}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ProjectsExist, List<Long>> {

        @Autowired
        private ProjectRepository projectRepository;

        @Override
        public void initialize(ProjectsExist constraintAnnotation) {

        }

        @Override
        public boolean isValid(List<Long> ids, ConstraintValidatorContext context) {
            return ids == null
                    || ids.stream().allMatch((id) -> projectRepository.existsById(id));
        }
    }
}
