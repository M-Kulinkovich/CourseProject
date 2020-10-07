package com.itlab.group3.controllers.resources;

import com.itlab.group3.controllers.validation.ProjectExist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@ApiModel(value = "Feature resource")
public class FeatureResource extends AbstractResource {

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    @ApiModelProperty(value = "Feature's name", example = "Unlock")
    private String name;

    @Size(min = 0, max = 1000)
    @ApiModelProperty(value = "Feature's description", example = "Fingerprint unlock")
    private String description;

    @ProjectExist
    @NotNull
    @ApiModelProperty(value = "Project's id", example = "1")
    private Long projectId;

//    private List<Long> tasksId;

}
