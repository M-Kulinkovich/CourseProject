package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.ProjectMapper;
import com.itlab.group3.controllers.resources.ProjectResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.Project;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.ProjectService;
import com.itlab.group3.services.filter.filters.ProjectFilter;
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
@RequestMapping(value = "project", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Project", description = "Project Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class ProjectController {

    @NonNull
    ProjectService projectService;
    @NonNull
    ProjectMapper projectMapper;

    @ApiResponse(code = 200, message = "Success",
            response = ProjectResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available Project")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ProjectResource>> getAll(@RequestParam(name = "filter", required = false) List<ProjectFilter> filters, @ApiIgnore @CurrentUser(required = true) User currentUser) {
        return ResponseEntity.ok(
                projectMapper.toResource(
                        projectService.findAll(filters, currentUser)
                )
        );
    }

    @ApiOperation(value = "Get a Project by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#project, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<ProjectResource> getById(@PathVariable("id") Project project) {
        return ResponseEntity.ok(
                projectMapper.toResource(project)
        );
    }

    @ApiOperation(value = "Add a Project")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<ProjectResource> save(@Valid @RequestBody ProjectResource projectResource) {
        return ResponseEntity.ok(
                projectMapper.toResource(
                        projectService.save(
                                projectMapper.toModel(projectResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a Project by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<ProjectResource> update(@PathVariable("id") Project current, @Valid @RequestBody ProjectResource projectResource) {

        return ResponseEntity.ok(
                projectMapper.toResource(
                        projectService.save(
                                projectMapper.update(current, projectResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a Project by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ProjectResource> delete(@PathVariable("id") Project project) {
        projectService.delete(project);
        return ResponseEntity.noContent().build();
    }
}
