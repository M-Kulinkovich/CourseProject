package com.itlab.group3.services;

import com.itlab.group3.dao.model.Report;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.common.FindAllFilterUserRoleSeparationService;

public interface ReportService extends CommonCrudService<Report>, FindAllFilterUserRoleSeparationService<Report> {
    Report update(Report report, User user);

    Report confirm(Report report, User currentUser, boolean confirm);
}
