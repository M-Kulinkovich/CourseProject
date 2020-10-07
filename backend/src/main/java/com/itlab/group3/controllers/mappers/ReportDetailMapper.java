package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.ReportDetailResource;
import com.itlab.group3.dao.model.ReportDetail;
import org.springframework.stereotype.Component;

@Component
public class ReportDetailMapper implements Mapper<ReportDetail, ReportDetailResource> {
    @Override
    public ReportDetailResource toResource(ReportDetail reportDetail) {
        return ReportDetailResource.builder()
                .id(reportDetail.getId())
                .body(reportDetail.getBody())
                .build();
    }

    @Override
    public ReportDetail toModel(ReportDetailResource resource) {
        return ReportDetail.builder()
                .body(resource.getBody())
                .build();
    }

    @Override
    public ReportDetail update(ReportDetail current, ReportDetailResource resource) {
        current.setBody(resource.getBody());
        return current;
    }
}
