package com.itlab.group3.services;

import com.itlab.group3.dao.model.Task;
import com.itlab.group3.services.common.FindAllFilterService;
import com.itlab.group3.services.common.FindAllFilterUserRoleSeparationService;

public interface TaskService extends CommonCrudService<Task>, FindAllFilterUserRoleSeparationService<Task> {
}
