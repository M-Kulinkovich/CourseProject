package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.ReportRepository;
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
@Constraint(validatedBy = ReportExist.Validator.class)
public @interface ReportExist {

    String message() default "{report doesnt exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ReportExist, Long> {

        @Autowired
        private ReportRepository reportRepository;

        public void initialize(ReportExist constraint) {
        }

        public boolean isValid(Long id, ConstraintValidatorContext context) {
            return id == null || reportRepository.existsById(id);
        }

    }
}
