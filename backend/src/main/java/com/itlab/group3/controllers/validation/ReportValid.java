package com.itlab.group3.controllers.validation;

import com.itlab.group3.controllers.resources.ReportResource;
import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.model.ReportRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ReportValid.Validator.class)
public @interface ReportValid {

    String message() default "{report doesnt valid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ReportValid, ReportResource> {

        @Autowired
        ReportRecordRepository reportRecordRepository;

        public void initialize(ReportValid constraint) {

        }

        @Override
        public boolean isValid(ReportResource reportResource, ConstraintValidatorContext context) {

            List<ReportRecord> reportRecords = StreamSupport
                    .stream(
                            reportRecordRepository
                                    .findAllById(reportResource.getReportRecordId()).spliterator(), false)
                    .collect(Collectors.toList());

            return reportRecords.parallelStream().mapToInt(ReportRecord::getWorkUnit).sum() <= reportRecords.parallelStream().mapToInt(ReportRecord::getHour).sum();
        }

    }
}
