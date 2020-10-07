package com.itlab.group3.services;

import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.services.common.FindAllFilterService;
import com.itlab.group3.services.common.FindAllFilterUserRoleSeparationService;

public interface FeatureService extends CommonCrudService<Feature>, FindAllFilterUserRoleSeparationService<Feature> {

}
