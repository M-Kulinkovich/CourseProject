package com.itlab.group3.controllers;

import com.itlab.group3.controllers.mappers.ReportDetailMapper;
import com.itlab.group3.controllers.resources.ReportDetailResource;
import com.itlab.group3.dao.model.ReportDetail;
import com.itlab.group3.services.ReportDetailService;
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
@RequestMapping(value = "/reportDetail", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "ReportDetail", description = "ReportDetail Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class ReportDetailController {

    @NonNull
    private final ReportDetailService reportDetailService;

    @NonNull
    private final ReportDetailMapper reportDetailMapper;

    @ApiResponse(code = 200, message = "Success",
            response = ReportDetailResource.class, responseContainer = "List")
    @ApiOperation(value = "View a list of available ReportDetail")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping
    public ResponseEntity<List<ReportDetailResource>> getAllReportDetail() {

        return ResponseEntity.ok(
                reportDetailMapper.toResource(reportDetailService.findAll()
                )
        );
    }

    @ApiOperation(value = "Get a ReportDetail by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#reportDetail, 'READ')")
    @GetMapping("{id}")
    public ResponseEntity<ReportDetailResource> getReportDetailById(
            @Valid @PathVariable("id") ReportDetail reportDetail) {

        return ResponseEntity.ok(
                reportDetailMapper.toResource(reportDetail)
        );
    }

    @ApiOperation(value = "Delete a ReportDetail by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#reportDetail, 'DELETE')")
    @DeleteMapping("{id}")
    public ResponseEntity<ReportDetailResource> deleteById(
            @Valid @PathVariable("id") ReportDetail reportDetail) {

        reportDetailService.delete(reportDetail);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Add a ReportDetail")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<ReportDetailResource> createReportDetail(
            @RequestBody @Valid ReportDetailResource reportDetailResource) {

        return ResponseEntity.ok(
                reportDetailMapper.toResource(
                        reportDetailService.save(
                                reportDetailMapper.toModel(reportDetailResource)
                        )
                )
        );
    }

    @ApiOperation(value = "Update a ReportDetail by Id")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @PreAuthorize("hasPermission(#current, 'UPDATE')")
    @PutMapping("{id}")
    public ResponseEntity<ReportDetailResource> updateReportDetail(
            @PathVariable("id") ReportDetail current,
            @Valid @RequestBody ReportDetailResource reportDetailResource) {

        return ResponseEntity.ok(
                reportDetailMapper.toResource(
                        reportDetailService.save(
                                reportDetailMapper.update(current, reportDetailResource)
                        )
                )
        );
    }
}
