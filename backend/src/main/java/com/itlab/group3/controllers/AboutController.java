package com.itlab.group3.controllers;

import com.itlab.group3.common.exception.NotFoundException;
import com.itlab.group3.controllers.mappers.AboutMapper;
import com.itlab.group3.controllers.resources.AboutResource;
import com.itlab.group3.dao.model.About;
import com.itlab.group3.services.AboutService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/about", produces = {"application/json"})
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Api(tags = "About", value = "About Management System")
public class AboutController {

    @NonNull
    private final AboutService aboutService;

    @NonNull
    private final AboutMapper aboutMapper;

    @GetMapping
    @ApiOperation(value = "About", response = About.class)
    public ResponseEntity<AboutResource> getAbout() {
        return ResponseEntity.ok(aboutMapper.toResource(aboutService.getAbout()
                .orElseThrow(() -> new NotFoundException("There is no any record about in DB"))));
    }
}
