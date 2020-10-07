package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.TaskResource;
import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.dao.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class TaskMapper implements Mapper<Task, TaskResource> {

    @NonNull
    private final ConversionService conversionService;

    @Override
    public Task toModel(TaskResource resource) {
        return Task.builder()
                .name(resource.getName())
                .description(resource.getDescription())
                .feature(conversionService.convert(resource.getFeatureId(), Feature.class))
                .build();
    }

    @Override
    public Task update(Task current, TaskResource resource) {
        current.setName(resource.getName());
        current.setDescription(resource.getDescription());
        current.setFeature(conversionService.convert(resource.getFeatureId(), Feature.class));
        return current;
    }

    @Override
    public TaskResource toResource(Task task) {
        return TaskResource.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .featureId(task.getFeature().getId())
//                .detailedTaskId(task.getDetailedTasks() != null ? task.getDetailedTasks().getId() : null)
                .build();
    }
}
