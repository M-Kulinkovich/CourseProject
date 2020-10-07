package com.itlab.group3.dao;

import com.itlab.group3.dao.model.Report;
import com.itlab.group3.dao.model.User;

public interface ReportRepository extends CommonCrudSpecificationRepository<Report> {
    boolean existsByUser(User user);
}
