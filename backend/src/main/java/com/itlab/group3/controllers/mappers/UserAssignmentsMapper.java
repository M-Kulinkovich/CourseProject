package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.UserAssignmentsResource;
import com.itlab.group3.dao.model.AbstractEntity;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.dao.model.UserAssignments;
import com.itlab.group3.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class UserAssignmentsMapper implements Mapper<UserAssignments, UserAssignmentsResource> {

    @NonNull
    private final ConversionService conversionService;

    @NonNull
    private final ProjectService projectService;

    @Override
    public UserAssignments toModel(UserAssignmentsResource resource) {
        return UserAssignments.builder()
                .user(conversionService.convert(resource.getUserId(), User.class))
                .projects(resource.getProjectIds().stream().map(
                        projectId -> projectService.findById(projectId).get())
                        .collect(Collectors.toList()))
                .build();
    }

    @Override
    public UserAssignments update(UserAssignments current, UserAssignmentsResource resource) {
        throw new UnsupportedOperationException();
    }

    @Override
    public UserAssignmentsResource toResource(UserAssignments userProject) {

        return UserAssignmentsResource.builder()
                .userId(userProject.getUser().getId())
                .id(userProject.getId())
                .projectIds(userProject.getProjects().stream().map(
                        AbstractEntity::getId).collect(Collectors.toList()))
                .build();
    }
}
