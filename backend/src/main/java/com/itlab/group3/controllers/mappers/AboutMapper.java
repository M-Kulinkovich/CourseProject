package com.itlab.group3.controllers.mappers;

import com.itlab.group3.controllers.resources.AboutResource;
import com.itlab.group3.dao.model.About;
import org.springframework.stereotype.Component;

@Component
public class AboutMapper implements ResourceMapper<About, AboutResource> {

    @Override
    public AboutResource toResource(About about) {
        return AboutResource.builder()
                .aboutBody(about.getBody())
                .build();
    }
}
