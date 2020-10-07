package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.ReportRecordMapper;
import com.itlab.group3.controllers.resources.ReportRecordResource;
import com.itlab.group3.controllers.validation.CurrentUser;
import com.itlab.group3.dao.model.ReportRecord;
import com.itlab.group3.dao.model.User;
import com.itlab.group3.services.ReportRecordService;
import com.itlab.group3.services.filter.filters.ReportRecordFilter;
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
@RequestMapping(value = "reportRecord", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "ReportRecord", description = "ReportRecord Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class ReportRecordController {

    @NonNull
    ReportRecordService reportRecordService;
    @NonNull
    ReportRecordMapper reportRecordMapper;

    @ApiResponse(code = 200, message = "Success",
            response = ReportRecordResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available ReportRecord")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<List<ReportRecordResource>> getAll(
            @RequestParam(name = "filter", required = false) List<ReportRecordFilter> filters,
            @ApiIgnore @CurrentUser(required = true) User currentUser) {

        return ResponseEntity.ok(
                reportRecordMapper.toResource(
                        reportRecordService.findAll(filters, currentUser)
                )
        );
    }

    @ApiOperation(value = "Get a ReportRecord by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#reportRecord, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<ReportRecordResource> getById(@PathVariable("id") ReportRecord reportRecord) {
        return ResponseEntity.ok(
                reportRecordMapper.toResource(reportRecord)
        );
    }

    @ApiOperation(value = "Add a ReportRecord")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<ReportRecordResource> save(@Valid @RequestBody ReportRecordResource reportRecordResource) {
        return ResponseEntity.ok(
                reportRecordMapper.toResource(
                        reportRecordService.save(
                                reportRecordMapper.toModel(reportRecordResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a ReportRecord by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#current, 'UPDATE')")
    @PutMapping("{id}")
    public ResponseEntity<ReportRecordResource> update(
            @PathVariable("id") ReportRecord current,
            @Valid @RequestBody ReportRecordResource reportRecordResource,
            @ApiIgnore @CurrentUser(required = true) User currentUser) {

        return ResponseEntity.ok(
                reportRecordMapper.toResource(
                        reportRecordService.update(
                                reportRecordMapper.update(current, reportRecordResource),
                                currentUser
                        )
                )
        );
    }

    @ApiOperation(value = "Delete a ReportRecord by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
//    @PreAuthorize("hasPermission(#reportRecord, 'DELETE')")
    @DeleteMapping("{id}")
    public ResponseEntity<ReportRecordResource> delete(@PathVariable("id") ReportRecord reportRecord) {
        reportRecordService.delete(reportRecord);
        return ResponseEntity.noContent().build();
    }

}
