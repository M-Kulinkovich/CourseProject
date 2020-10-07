package com.itlab.group3.controllers.resources;

import com.itlab.group3.controllers.validation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@Builder
@ReportRecordValid
@ApiModel(value = "ReportRecord resource")
public class ReportRecordResource extends AbstractResource {

    @ReportExist
    @ApiModelProperty(value = "report's id",example = "1")
    private Long reportId;

    @ProjectExist
    @ApiModelProperty(value = "project's id",example = "1")
    private Long projectId;

    @FeatureExist
    @ApiModelProperty(value = "feature's id",example = "1")
    private Long featureId;

    @TaskExist
    @ApiModelProperty(value = "task's id",example = "1")
    private Long taskId;

    @DetailedTaskExist
    @ApiModelProperty(value = "detailedTask's id",example = "1")
    private Long detailedTaskId;

    @Size(min = 0, max = 2000)
    @ApiModelProperty(value = "Text",example = "text")
    private String text;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(value = "Working hours",example = "2020-02-12")
    private LocalDate date;

    @UserExist
    @ApiModelProperty(value = "user's id",example = "1")
    private Long userId;

    @Min(15)
    @Max(24 * 60)
    @ApiModelProperty(value = "Hour (hour >= Work unit)",example = "12")
    private Integer hour;

    @Min(15)
    @Max(24 * 60)
    @ApiModelProperty(value = "Work unit(Work unit <= Hour)",example = "6")
    private Integer workUnit;

    @FactorExist
    @ApiModelProperty(value = "Type of work, for example, standard," +
            " processing, vacation, business trip, etc.",
            example = "STANDARD")
    private String factor;

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @LocationExist
    @NotNull
    @ApiModelProperty(value = "Location's id",example = "1")
    private Long locationId;

}
