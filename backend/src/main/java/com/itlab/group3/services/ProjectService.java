package com.itlab.group3.services;

import com.itlab.group3.dao.model.Project;
import com.itlab.group3.services.common.FindAllFilterService;
import com.itlab.group3.services.common.FindAllFilterUserRoleSeparationService;

public interface ProjectService extends CommonCrudService<Project>, FindAllFilterUserRoleSeparationService<Project> {

}
