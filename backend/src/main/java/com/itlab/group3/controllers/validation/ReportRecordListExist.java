package com.itlab.group3.controllers.validation;

import com.itlab.group3.dao.ReportRecordRepository;
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
@Constraint(validatedBy = ReportRecordListExist.Validator.class)
public @interface ReportRecordListExist {

    String message() default "{one or more recordRecord does not exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ReportRecordListExist, List<Long>> {

        @Autowired
        private ReportRecordRepository reportRecordRepository;


        public void initialize(ReportRecordListExist constraint) {
        }

        public boolean isValid(List<Long> l, ConstraintValidatorContext context) {
            return l.stream().allMatch(reportRecordRepository::existsById);
        }
    }
}
