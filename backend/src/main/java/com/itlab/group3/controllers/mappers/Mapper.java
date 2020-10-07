package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.AbstractResource;
import com.itlab.group3.dao.model.AbstractEntity;

public interface Mapper<MODEL extends AbstractEntity, RESOURCE extends AbstractResource> extends ResourceMapper<MODEL, RESOURCE> {
    MODEL toModel(RESOURCE resource);

    MODEL update(MODEL current, RESOURCE resource);
}
