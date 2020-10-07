package com.itlab.group3.controllers.validation;


import com.itlab.group3.controllers.resources.ReportRecordResource;
import com.itlab.group3.dao.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = ReportRecordValid.Validator.class)
public @interface ReportRecordValid {

    String message() default "{reportRecord doesnt exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<ReportRecordValid, ReportRecordResource> {

        @Autowired
        private ConversionService conversionService;

        public void initialize(ReportRecordValid constraint) {
        }

        private boolean isProjectValid(Project project, Feature feature) {
            return feature == null || (project != null && feature.getProject().getId().equals(project.getId()));
        }

        private boolean isFeatureValid(Feature feature, Task task) {
            return task == null || (feature != null && task.getFeature().getId().equals(feature.getId()));
        }


        private boolean isTaskValid(Task task, DetailedTask detailedTask) {
            return detailedTask == null || (task != null && detailedTask.getTask().getId().equals(task.getId()));
        }

        public boolean isValid(ReportRecordResource resource, ConstraintValidatorContext context) {
            Project project = conversionService.convert(resource.getProjectId(), Project.class);
            Feature feature = conversionService.convert(resource.getFeatureId(), Feature.class);
            Task task = conversionService.convert(resource.getTaskId(), Task.class);
            DetailedTask detailedTask = conversionService.convert(resource.getDetailedTaskId(), DetailedTask.class);

            Factor factor = conversionService.convert(resource.getFactor(), Factor.class);

            return resource.getWorkUnit() <= resource.getHour()
                    && isProjectValid(project, feature)
                    && isFeatureValid(feature, task)
                    && isTaskValid(task, detailedTask)
                    &&
                    (factor == Factor.STANDARD
                            || factor == Factor.OVERTIME
                            || factor == Factor.BUSINESS_TRIP
                            || project == null);
        }
    }
}
