package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.LocationRepository;
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
@Constraint(validatedBy = LocationExist.Validator.class)
public @interface LocationExist {


    String message() default "{location doesnt exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<LocationExist, Long> {

        @Autowired
        private LocationRepository locationRepository;

        public void initialize(LocationExist constraint) {
        }

        public boolean isValid(Long id, ConstraintValidatorContext context) {
            return id == null || locationRepository.existsById(id);
        }
    }
}
