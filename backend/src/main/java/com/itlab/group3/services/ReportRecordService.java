package com.itlab.group3.services;

import com.itlab.group3.dao.model.ReportRecord;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.common.FindAllFilterUserRoleSeparationService;

public interface ReportRecordService extends CommonCrudService<ReportRecord>, FindAllFilterUserRoleSeparationService<ReportRecord> {
    ReportRecord update(ReportRecord report, User user);
}
