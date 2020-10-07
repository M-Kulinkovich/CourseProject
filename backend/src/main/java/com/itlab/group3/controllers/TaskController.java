package com.itlab.group3.controllers;


import com.itlab.group3.controllers.mappers.TaskMapper;
import com.itlab.group3.controllers.resources.TaskResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.Task;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.TaskService;
import com.itlab.group3.services.filter.filters.TaskFilter;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "task", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Task", description = "Task Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class TaskController {

    @NonNull
    TaskService taskService;

    @NonNull
    TaskMapper taskMapper;

    @ApiResponse(code = 200, message = "Success",
            response = TaskResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available Task")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<TaskResource>> getAll(@RequestParam(name = "filter", required = false) List<TaskFilter> filters,@ApiIgnore @CurrentUser(required = true) User currentUser) {
        return ResponseEntity.ok(
                taskMapper.toResource(
                        taskService.findAll(filters, currentUser)
                )
        );
    }

    @ApiOperation(value = "Get a Task by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#task, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<TaskResource> getById(@PathVariable("id") Task task) {
        return ResponseEntity.ok(
                taskMapper.toResource(task)
        );
    }

    @ApiOperation(value = "Add a Task")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<TaskResource> save(@Valid @RequestBody TaskResource TaskResource) {
        return ResponseEntity.ok(
                taskMapper.toResource(
                        taskService.save(
                                taskMapper.toModel(TaskResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a Task by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<TaskResource> update(@PathVariable("id") Task current, @Valid @RequestBody TaskResource taskResource) {
        return ResponseEntity.ok(
                taskMapper.toResource(
                        taskService.save(
                                taskMapper.update(current, taskResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a Task by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<TaskResource> delete(@PathVariable("id") Task task) {
        taskService.delete(task);
        return ResponseEntity.noContent().build();
    }


}
