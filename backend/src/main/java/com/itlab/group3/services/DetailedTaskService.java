package com.itlab.group3.services;

import com.itlab.group3.dao.model.DetailedTask;
import com.itlab.group3.services.common.FindAllFilterService;
import com.itlab.group3.services.common.FindAllFilterUserRoleSeparationService;

public interface DetailedTaskService extends CommonCrudService<DetailedTask>, FindAllFilterUserRoleSeparationService<DetailedTask> {
}
