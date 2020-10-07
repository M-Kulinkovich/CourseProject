package com.itlab.group3.controllers.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@ApiModel(value = "About resource")
public class AboutResource extends AbstractResource {

    @ApiModelProperty(value = "Information about project")
    private String aboutBody;

    @ApiModelProperty(value = "version")
    private String version;

}
