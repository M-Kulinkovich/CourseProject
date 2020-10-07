package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.ProjectResource;
import com.itlab.group3.dao.model.Project;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper implements Mapper<Project, ProjectResource> {


    @Override
    public Project toModel(ProjectResource projectResource) {

        return Project.builder()
                .name(projectResource.getName())
                .description(projectResource.getDescription())
                .build();
    }

    @Override
    public Project update(Project current, ProjectResource resource) {
        current.setDescription(resource.getDescription());
        current.setName(resource.getName());
        return current;
    }

    @Override
    public ProjectResource toResource(Project project) {
        return ProjectResource.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
//                .featuresId(MapperFunctions.toIdList(project.getFeatures()))
                .build();
    }
}
