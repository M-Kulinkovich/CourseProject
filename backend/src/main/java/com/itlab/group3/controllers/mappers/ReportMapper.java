package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.exception.ValidException;
import com.itlab.group3.controllers.resources.ReportResource;
import com.itlab.group3.controllers.security.UserAgent;
import com.itlab.group3.dao.ReportRecordRepository;
import com.itlab.group3.dao.model.AbstractEntity;
import com.itlab.group3.dao.model.Report;
import com.itlab.group3.dao.model.ReportRecord;
import com.itlab.group3.dao.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class ReportMapper implements Mapper<Report, ReportResource> {

    @NonNull
    private final ConversionService conversionService;

    @NonNull
    private final UserAgent userAgent;

    @NonNull
    private final ReportRecordRepository reportRecordRepository;

    void userValid(List<Long> reportRecordId, Long userId) {
        if (!StreamSupport
                .stream(
                        reportRecordRepository
                                .findAllById(reportRecordId).spliterator(),
                        false)
                .allMatch(e -> e.getUser().getId().equals(userId))) {
            throw new ValidException("report user does not match reportRecord user");
        }
    }

    @Override
    public Report toModel(ReportResource resource) {
        User currentUser = userAgent.getUser().orElseThrow(RuntimeException::new);
        if (resource.getUserId() == null || !currentUser.isAdmin()) {
            resource.setUserId(currentUser.getId());
        }

        userValid(resource.getReportRecordId(), resource.getUserId());

        List<ReportRecord> reportRecords = resource.getReportRecordId()
                .stream()
                .map(e -> conversionService.convert(e, ReportRecord.class))
                .collect(Collectors.toList());

        return Report.builder()
                .confirm(resource.getConfirm())
                .date(resource.getDate())
                .user(conversionService.convert(resource.getUserId(), User.class))
                .reportRecords(reportRecords)
                .hour(reportRecords.parallelStream().mapToInt(ReportRecord::getHour).sum())
                .workUnit(reportRecords.parallelStream().mapToInt(ReportRecord::getWorkUnit).sum())
                .build();
    }

    @Override
    public Report update(Report current, ReportResource resource) {
        userValid(resource.getReportRecordId(), current.getUser().getId());

        current.setConfirm(resource.getConfirm());
        current.setDate(resource.getDate());
        current.setReportRecords(
                resource.getReportRecordId()
                        .stream()
                        .map(e -> conversionService.convert(e, ReportRecord.class))
                        .collect(Collectors.toList())
        );
        current.setHour(current.getReportRecords().parallelStream().mapToInt(ReportRecord::getHour).sum());
        current.setWorkUnit(current.getReportRecords().parallelStream().mapToInt(ReportRecord::getWorkUnit).sum());
        return current;
    }

    @Override
    public ReportResource toResource(Report report) {
        return ReportResource.builder()
                .confirm(report.getConfirm())
                .date(report.getDate())
                .userId(report.getUser().getId())
                .reportRecordId(
                        report
                                .getReportRecords()
                                .stream()
                                .map(AbstractEntity::getId)
                                .collect(Collectors.toList())
                )
                .hour(report.getHour())
                .workUnit(report.getWorkUnit())
                .id(report.getId())
                .build();
    }
}
