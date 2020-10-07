package com.itlab.group3.controllers.resources;

import com.itlab.group3.controllers.validation.FeatureExist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ApiModel(value = "Task resource")
public class TaskResource extends AbstractResource {

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "Task's name", example = "Entity user")
    private String name;

    @Size(min = 0, max = 1000)
    @ApiModelProperty(value = "Task's name", example = "Create entity user")
    private String description;

    @FeatureExist
    @NotNull
    @ApiModelProperty(value = "Feature's id", example = "1")
    private Long featureId;

//    private Long detailedTaskId;
}
