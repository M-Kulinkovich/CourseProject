package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.LocationResource;
import com.itlab.group3.dao.model.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper implements Mapper<Location, LocationResource> {
    @Override
    public Location toModel(LocationResource resource) {
        return Location.builder()
                .name(resource.getName())
                .build();
    }

    @Override
    public Location update(Location current, LocationResource resource) {
        current.setName(resource.getName());
        return current;
    }

    @Override
    public LocationResource toResource(Location location) {
        return LocationResource.builder()
                .id(location.getId())
                .name(location.getName())
                .build();
    }
}
