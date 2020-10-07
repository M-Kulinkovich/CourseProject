package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.UserRepository;
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
@Constraint(validatedBy = UserExist.Validator.class)
public @interface UserExist {

    String message() default "{user doesnt exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<UserExist, Long> {

        @Autowired
        private UserRepository userRepository;

        public void initialize(UserExist constraint) {
        }

        public boolean isValid(Long id, ConstraintValidatorContext context) {
            return id == null || userRepository.existsById(id);
        }
    }
}
