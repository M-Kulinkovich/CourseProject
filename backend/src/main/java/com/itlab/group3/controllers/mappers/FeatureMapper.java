package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.FeatureResource;
import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.dao.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class FeatureMapper implements Mapper<Feature, FeatureResource> {

    @NonNull
    private final ConversionService conversionService;


    @Override
    public Feature toModel(FeatureResource featureResource) {
        return Feature.builder()
                .name(featureResource.getName())
                .description(featureResource.getDescription())
                .project(conversionService.convert(featureResource.getProjectId(), Project.class))
                .build();
    }

    @Override
    public Feature update(Feature current, FeatureResource resource) {
        current.setProject(conversionService.convert(resource.getProjectId(), Project.class));
        current.setDescription(resource.getDescription());
        current.setName(resource.getName());
        return current;
    }

    @Override
    public FeatureResource toResource(Feature feature) {
        return FeatureResource.builder()
                .name(feature.getName())
                .description(feature.getDescription())
                .projectId(feature.getProject().getId())
                .id(feature.getId())
//                .tasksId(MapperFunctions.toIdList(feature.getTasks()))
                .build();
    }
}
