package com.itlab.group3.controllers;

import com.itlab.group3.dao.model.Factor;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/factor", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "Factor", description = "Factor Management System")
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
})

public class FactorController {

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @ApiResponse(code = 200, message = "Success",
            response = Factor.class, responseContainer = "List")
    @ApiOperation(value = "View a list Factor")
    @ApiImplicitParam(name = "Authorization",
            value = "Bearer token",
            paramType = "header", example = "Bearer token",
            required = true)
    @GetMapping
    public ResponseEntity<Factor[]> getAll() {

        return ResponseEntity.ok(
                Factor.values()
        );
    }

}
