package com.itlab.group3.controllers.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ApiModel(value = "Location resource")
public class LocationResource extends AbstractResource {

    @NotNull
    @Size(min = 1, max = 256)
    @ApiModelProperty(value = "Place of work on the project",example = "Home")
    String name;

    @ApiModelProperty(value = "Id", example = "1")
    Long id;
}
