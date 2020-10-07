package com.itlab.group3.controllers.resources;

import com.itlab.group3.controllers.validation.TaskExist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ApiModel(value = "DetailedTask resource")
public class DetailedTaskResource extends AbstractResource {

    @ApiModelProperty(value = "Id detailedTask", example = "1")
    private Long id;

    @ApiModelProperty(value = "Detailed Task", example = "example")
    @Size(min = 0, max = 2000)
    private String text;

    @TaskExist
    @NotNull
    @ApiModelProperty(value = "Task's id", example = "1")
    private Long taskId;

}
