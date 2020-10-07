package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.DetailedTaskResource;
import com.itlab.group3.dao.model.DetailedTask;
import com.itlab.group3.dao.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class DetailedTaskMapper implements Mapper<DetailedTask, DetailedTaskResource> {

    @NonNull
    private final ConversionService conversionService;

    @Override
    public DetailedTask toModel(DetailedTaskResource resource) {
        return DetailedTask.builder()
                .text(resource.getText())
                .task(conversionService.convert(resource.getTaskId(), Task.class))
                .build();
    }

    @Override
    public DetailedTask update(DetailedTask current, DetailedTaskResource resource) {
        current.setTask(conversionService.convert(resource.getTaskId(), Task.class));
        current.setText(resource.getText());
        return current;
    }

    @Override
    public DetailedTaskResource toResource(DetailedTask detailedTask) {
        return DetailedTaskResource.builder()
                .id(detailedTask.getId())
                .text(detailedTask.getText())
                .taskId(detailedTask.getTask().getId())
                .build();
    }
}
