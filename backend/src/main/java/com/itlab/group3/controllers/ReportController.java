package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.ReportMapper;
import com.itlab.group3.controllers.resources.ReportResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.Report;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.ReportService;
import com.itlab.group3.services.filter.filters.ReportFilter;
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
@RequestMapping(value = "report", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Report", description = "Report Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})
public class ReportController {
    @NonNull
    ReportService reportService;
    @NonNull
    ReportMapper reportMapper;

    @ApiResponse(code = 200, message = "Success",
            response = ReportResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available Report")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ReportResource>> getAll(
            @RequestParam(name = "filter", required = false) List<ReportFilter> filters,
            @ApiIgnore @CurrentUser(required = true) User currentUser) {

        return ResponseEntity.ok(
                reportMapper.toResource(
                        reportService.findAll(filters, currentUser)
                )
        );
    }

    @ApiOperation(value = "Get a Report by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#report, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<ReportResource> getById(@PathVariable("id") Report report) {
        return ResponseEntity.ok(
                reportMapper.toResource(report)
        );
    }


    @ApiOperation(value = "Add a Report")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<ReportResource> save(@Valid @RequestBody ReportResource reportResource) {
        return ResponseEntity.ok(
                reportMapper.toResource(
                        reportService.save(
                                reportMapper.toModel(reportResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a Report by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#current, 'UPDATE')")
    @PutMapping("{id}")
    public ResponseEntity<ReportResource> update(
            @PathVariable("id") Report current,
            @Valid @RequestBody ReportResource reportResource,
            @ApiIgnore @CurrentUser(required = true) User currentUser) {

        return ResponseEntity.ok(
                reportMapper.toResource(
                        reportService.update(
                                reportMapper.update(current, reportResource),
                                currentUser
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a Report by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#report, 'DELETE')")
    @DeleteMapping("{id}")
    public ResponseEntity<ReportResource> delete(@PathVariable("id") Report report) {
        reportService.delete(report);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Confirm Report")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#report, 'UPDATE')")
    @PutMapping("confirm/{id}")
    public ResponseEntity<ReportResource> confirm(
            @PathVariable("id") Report report,
            @ApiIgnore @CurrentUser(required = true) User currentUser,
            @RequestParam(required = false, defaultValue = "true") boolean confirm) {

        return ResponseEntity.ok(
                reportMapper.toResource(
                        reportService.confirm(
                                report, currentUser, confirm
                        )
                )
        );
    }


}
