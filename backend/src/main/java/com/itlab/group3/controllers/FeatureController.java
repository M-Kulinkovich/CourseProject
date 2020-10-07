package com.itlab.group3.controllers;


import com.itlab.group3.controllers.mappers.FeatureMapper;
import com.itlab.group3.controllers.resources.FeatureResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.Feature;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.FeatureService;
import com.itlab.group3.services.filter.filters.FeatureFilter;
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
@RequestMapping(value = "feature", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Feature", description = "Feature Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class FeatureController {

    @NonNull
    FeatureService featureService;

    @NonNull
    FeatureMapper featureMapper;

    @ApiResponse(code = 200, message = "Success",
            response = FeatureResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available Feature")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<FeatureResource>> getAll(@RequestParam(name = "filter", required = false) List<FeatureFilter> filters,@ApiIgnore @CurrentUser(required = true) User currentUser) {
        return ResponseEntity.ok(
                featureMapper.toResource(
                        featureService.findAll(filters, currentUser)
                )
        );
    }

    @ApiOperation(value = "Get a Feature by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#feature, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<FeatureResource> getById(@PathVariable("id") Feature feature) {
        return ResponseEntity.ok(
                featureMapper.toResource(feature)
        );
    }

    @ApiOperation(value = "Add a Feature")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<FeatureResource> save(@Valid @RequestBody FeatureResource FeatureResource) {
        return ResponseEntity.ok(
                featureMapper.toResource(
                        featureService.save(
                                featureMapper.toModel(FeatureResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a Feature by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<FeatureResource> update(@PathVariable("id") Feature current, @Valid @RequestBody FeatureResource FeatureResource) {
        return ResponseEntity.ok(
                featureMapper.toResource(
                        featureService.save(
                                featureMapper.update(current, FeatureResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a Feature by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<FeatureResource> delete(@PathVariable("id") Feature feature) {
        featureService.delete(feature);
        return ResponseEntity.noContent().build();
    }


}
