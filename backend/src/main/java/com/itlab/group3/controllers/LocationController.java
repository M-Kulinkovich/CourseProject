package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.LocationMapper;
import com.itlab.group3.controllers.resources.LocationResource;
import com.itlab.group3.dao.model.Location;
import com.itlab.group3.services.LocationService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "location", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Location", description = "Location Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class LocationController {

    @NonNull
    LocationService locationService;
    @NonNull
    LocationMapper locationMapper;

    @ApiResponse(code = 200, message = "Success",
            response = LocationResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available Location")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<LocationResource>> getAll() {
        return ResponseEntity.ok(
                locationMapper.toResource(
                        locationService.findAll()
                )
        );
    }

    @ApiOperation(value = "Get a Location by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("{id}")
    public ResponseEntity<LocationResource> getById(@PathVariable("id") Location location) {
        return ResponseEntity.ok(
                locationMapper.toResource(location)
        );
    }

    @ApiOperation(value = "Add a Location")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<LocationResource> save(@Valid @RequestBody LocationResource locationResource) {
        return ResponseEntity.ok(
                locationMapper.toResource(
                        locationService.save(
                                locationMapper.toModel(locationResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a Location by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<LocationResource> update(@PathVariable("id") Location current, @Valid @RequestBody LocationResource locationResource) {

        return ResponseEntity.ok(
                locationMapper.toResource(
                        locationService.save(
                                locationMapper.update(current, locationResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a Location by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<LocationResource> delete(@PathVariable("id") Location location) {
        locationService.delete(location);
        return ResponseEntity.noContent().build();
    }
}
