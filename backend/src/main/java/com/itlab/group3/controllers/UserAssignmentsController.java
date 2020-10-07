package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.UserAssignmentsMapper;
import com.itlab.group3.controllers.resources.UserAssignmentsResource;
import com.itlab.group3.dao.model.Project;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.dao.model.UserAssignments;
import com.itlab.group3.services.UserAssignmentsService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@RestController
@RequestMapping(value = "/usersAssignments", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "UserAssignments", description = "UserAssignments Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class UserAssignmentsController {

    @NotNull
    private final UserAssignmentsService userAssignmentsService;

    @NotNull
    private final UserAssignmentsMapper userAssignmentsMapper;

    @ApiOperation(value = "Get an UserAssignments by userId")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAssignmentsResource> getUserAssignById(@PathVariable("userId") User user) {

        Optional<UserAssignments> userProject = userAssignmentsService.findByUser(user);
        if (userProject.isPresent()) {
            return ResponseEntity.ok(
                    userAssignmentsMapper.toResource(userProject.get()));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(value = "Create or update a UserAssignments by userId")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/user/{userId}/project/{projectId}")
    public ResponseEntity<UserAssignmentsResource> updateUserAssign(
            @PathVariable("userId") User user,
            @PathVariable("projectId") Project project, @RequestParam boolean isAssign) {

        return ResponseEntity.ok(
                userAssignmentsMapper.toResource(
                        userAssignmentsService.assigning(
                                user,
                                project,
                                isAssign
                        )
                )
        );
    }

    @ApiOperation(value = "Delete an UserAssignments by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<UserAssignmentsResource> deleteById(
            @Valid @PathVariable("id") UserAssignments userAssignments) {

        userAssignmentsService.delete(userAssignments);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Delete an UserAssignments by userId")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<UserAssignmentsResource> deleteByUser(@PathVariable("userId") User user) {

        userAssignmentsService.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }
}
