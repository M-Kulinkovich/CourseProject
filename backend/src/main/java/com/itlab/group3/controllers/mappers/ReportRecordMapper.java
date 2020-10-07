package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.ReportRecordResource;
import com.itlab.group3.controllers.security.UserAgent;
import com.itlab.group3.dao.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ReportRecordMapper implements Mapper<ReportRecord, ReportRecordResource> {

    @NonNull
    private final ConversionService conversionService;

    @NonNull
    private final UserAgent userAgent;


    @Override
    public ReportRecord toModel(ReportRecordResource resource) {
        User currentUser = userAgent.getUser().orElseThrow(RuntimeException::new);
        if (resource.getUserId() == null) {
            resource.setUserId(currentUser.getId());
        }
        return ReportRecord.builder()
                .date(resource.getDate())
                .detailedTask(conversionService.convert(resource.getDetailedTaskId(), DetailedTask.class))
                .feature(conversionService.convert(resource.getFeatureId(), Feature.class))
                .project(conversionService.convert(resource.getProjectId(), Project.class))
                .report(conversionService.convert(resource.getReportId(), Report.class))
                .task(conversionService.convert(resource.getTaskId(), Task.class))
                .text(resource.getText())
                .user(currentUser.isAdmin() ? conversionService.convert(resource.getUserId(), User.class) : currentUser)
                .hour(resource.getHour())
                .workUnit(resource.getWorkUnit())
                .factor(Factor.valueOf(resource.getFactor()))
                .location(conversionService.convert(resource.getLocationId(), Location.class))
                .build();
    }

    @Override
    public ReportRecord update(ReportRecord current, ReportRecordResource resource) {
        current.setDate(resource.getDate());
        current.setDetailedTask(conversionService.convert(resource.getDetailedTaskId(), DetailedTask.class));
        current.setFeature(conversionService.convert(resource.getFeatureId(), Feature.class));
        current.setProject(conversionService.convert(resource.getProjectId(), Project.class));
        current.setReport(conversionService.convert(resource.getReportId(), Report.class));
        current.setTask(conversionService.convert(resource.getTaskId(), Task.class));
        current.setText(resource.getText());
//        current.setUser(conversionService.convert(resource.getUserId(), User.class));
        current.setHour(resource.getHour());
        current.setWorkUnit(resource.getWorkUnit());
        current.setFactor(Factor.valueOf(resource.getFactor()));
        current.setLocation(conversionService.convert(resource.getLocationId(), Location.class));
        return current;
    }

    @Override
    public ReportRecordResource toResource(ReportRecord reportRecord) {
        return ReportRecordResource.builder()
                .date(reportRecord.getDate())
                .detailedTaskId(reportRecord.getDetailedTask() != null ? reportRecord.getDetailedTask().getId() : null)
                .featureId(reportRecord.getFeature() != null ? reportRecord.getFeature().getId() : null)
                .projectId(reportRecord.getProject() != null ? reportRecord.getProject().getId() : null)
                .reportId(reportRecord.getReport() != null ? reportRecord.getReport().getId() : null)
                .taskId(reportRecord.getTask() != null ? reportRecord.getTask().getId() : null)
                .text(reportRecord.getText())
                .userId(reportRecord.getUser() != null ? reportRecord.getUser().getId() : null)
                .hour(reportRecord.getHour())
                .workUnit(reportRecord.getWorkUnit())
                .factor(reportRecord.getFactor() != null ? reportRecord.getFactor().toString() : null)
                .id(reportRecord.getId())
                .locationId(reportRecord.getLocation().getId())
                .build();
    }
}
