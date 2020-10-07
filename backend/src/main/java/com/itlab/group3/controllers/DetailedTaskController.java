package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.DetailedTaskMapper;
import com.itlab.group3.controllers.resources.DetailedTaskResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.DetailedTask;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.DetailedTaskService;
import com.itlab.group3.services.filter.filters.DetailedTaskFilter;
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
@RequestMapping(value = "detailedTask", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "DetailedTask", description = "DetailedTask Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class DetailedTaskController {

    @NonNull
    DetailedTaskService detailedTaskService;

    @NonNull
    DetailedTaskMapper detailedTaskMapper;


    @ApiResponse(code = 200, message = "Success",
            response = DetailedTaskResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available DetailedTask")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<DetailedTaskResource>> getAll(@RequestParam(name = "filter", required = false) List<DetailedTaskFilter> filters, @ApiIgnore @CurrentUser(required = true) User currentUser) {
        return ResponseEntity.ok(
                detailedTaskMapper.toResource(
                        detailedTaskService.findAll(filters, currentUser)
                )
        );
    }

    @ApiOperation(value = "Get a DetailedTask by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#detailedTask, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<DetailedTaskResource> getById(@PathVariable("id") DetailedTask detailedTask) {

        return ResponseEntity.ok(
                detailedTaskMapper.toResource(detailedTask)
        );
    }

    @ApiOperation(value = "Add a DetailedTask")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<DetailedTaskResource> save(@Valid @RequestBody DetailedTaskResource DetailedTaskResource) {
        return ResponseEntity.ok(
                detailedTaskMapper.toResource(
                        detailedTaskService.save(
                                detailedTaskMapper.toModel(DetailedTaskResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a DetailedTask by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<DetailedTaskResource> update(@PathVariable("id") DetailedTask current, @Valid @RequestBody DetailedTaskResource detailedTaskResource) {

        return ResponseEntity.ok(
                detailedTaskMapper.toResource(
                        detailedTaskService.save(
                                detailedTaskMapper.update(current, detailedTaskResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a DetailedTask by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") DetailedTask detailedTask) {
        detailedTaskService.delete(detailedTask);
        return ResponseEntity.noContent().build();
    }

}
