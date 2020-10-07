package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.AbstractResource;
import com.itlab.group3.dao.model.AbstractEntity;

public abstract class AbstractResourceMapper {

    abstract AbstractResource fromToResource(AbstractEntity entity);
}
