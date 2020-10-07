package com.itlab.group3.controllers.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ApiModel(value = "Project resource")
public class ProjectResource extends AbstractResource {

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "Project's name", example = "example")
    private String name;

    @Size(min = 0, max = 1000)
    @ApiModelProperty(value = "Project's description", example = "example")
    private String description;

//    private List<Long> featuresId;
}
