package com.itlab.group3.controllers.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ReportDetail resource")
public class ReportDetailResource extends AbstractResource {

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @NotNull
    @ApiModelProperty(example = "Body")
    private String body;
}
