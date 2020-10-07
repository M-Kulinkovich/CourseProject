package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.AbstractResource;
import com.itlab.group3.dao.model.AbstractEntity;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface ResourceMapper<MODEL extends AbstractEntity, RESOURCE extends AbstractResource> {
    RESOURCE toResource(MODEL model);

    default List<RESOURCE> toResource(Iterable<MODEL> iterable) {
        return StreamSupport.stream(iterable.spliterator(), true)
                .map(this::toResource)
                .collect(Collectors.toList());
    }


}
