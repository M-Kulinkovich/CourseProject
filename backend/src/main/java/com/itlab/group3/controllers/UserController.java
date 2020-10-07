package com.itlab.group3.controllers;

import com.itlab.group3.common.exception.NotFoundException;
import com.itlab.group3.controllers.mappers.UserMapper;
import com.itlab.group3.controllers.resources.UserResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.UserService;
import io.swagger.annotations.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "User", description = "User Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class UserController {

    @NonNull
    private final UserService userService;

    @NonNull
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @ApiResponse(code = 200, message = "Success",
            response = UserResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available user")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        List<UserResource> userResource = userMapper.toResource(userService.findAll());
        return ResponseEntity.ok(userResource);
    }

    @ApiOperation(value = "Get an user by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#user, 'READ')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResource> getById(@PathVariable("id") User user) {
        UserResource userResource = userMapper.toResource(userService.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found")));
        return ResponseEntity.ok(userResource);
    }

    @ApiOperation(value = "Add an user")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResource> newUser(@Valid @RequestBody UserResource user) {
        UserResource userResource = userMapper.toResource(userService.save(userMapper.toModel(user)));
        return ResponseEntity.ok(userResource);
    }

    @ApiOperation(value = "Update an user by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#currentUser, 'UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable("id") User currentUser, @Valid @RequestBody UserResource resource) {
        UserResource userResource = userMapper.toResource(userService.save(userMapper.update(currentUser, resource)));
        return ResponseEntity.ok(userResource);
    }

    @ApiOperation(value = "Delete an user by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") User user) {
        userService.delete(user);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Get current user")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#user, 'READ')")
    @GetMapping("current")
    public ResponseEntity<UserResource> getCurrentUser(@CurrentUser(required = true) User user) {
        return ResponseEntity.ok(userMapper.toResource(user));
    }

}
