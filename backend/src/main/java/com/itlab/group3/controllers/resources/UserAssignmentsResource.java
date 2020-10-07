package com.itlab.group3.controllers.resources;

import com.itlab.group3.controllers.validation.ProjectsExist;
import com.itlab.group3.controllers.validation.UserExist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "UserAssignments resource")
public class UserAssignmentsResource extends AbstractResource {

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @UserExist
    @NotNull
    @ApiModelProperty(value = "User's id", example = "1")
    private Long userId;

    @ProjectsExist
    @NotNull
    @ApiModelProperty(value = "Project's id", example = "1")
    private List<Long> projectIds;
}
