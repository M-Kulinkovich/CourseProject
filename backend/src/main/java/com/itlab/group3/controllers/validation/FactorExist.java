package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.model.Factor;

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
@Constraint(validatedBy = FactorExist.Validator.class)
public @interface FactorExist {

    String message() default "{detailedTask doesnt exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<FactorExist, String> {

        @Override
        public void initialize(FactorExist constraintAnnotation) {

        }

        @Override
        public boolean isValid(String factor, ConstraintValidatorContext context) {
            boolean result = factor == null;
            if (factor != null) {
                try {
                    Factor.valueOf(factor);
                    result = true;
                } catch (Exception e) {
                    result = false;
                }
            }
            return result;
        }
    }
}
