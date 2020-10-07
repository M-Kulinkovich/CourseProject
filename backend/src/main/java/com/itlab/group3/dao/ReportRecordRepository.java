package com.itlab.group3.dao;

import com.itlab.group3.dao.model.*;

import java.util.List;

public interface ReportRecordRepository extends CommonCrudSpecificationRepository<ReportRecord> {

    boolean existsByProject(Project project);

    boolean existsByFeature(Feature feature);

    boolean existsByTask(Task task);

    boolean existsByDetailedTask(DetailedTask detailedTask);

    boolean existsByUser(User user);

    boolean existsByLocation(Location location);

    List<ReportRecord> findAllByReportId(Long reportId);

}
