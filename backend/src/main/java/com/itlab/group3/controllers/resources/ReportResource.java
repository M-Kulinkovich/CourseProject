package com.itlab.group3.controllers.resources;

import com.itlab.group3.controllers.validation.ReportRecordListExist;
import com.itlab.group3.controllers.validation.ReportValid;
import com.itlab.group3.controllers.validation.UserExist;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@ReportValid
@ApiModel(value = "Report resource")
public class ReportResource extends AbstractResource {

    @UserExist
    @ApiModelProperty(value = "user's id", example = "1")
    private Long userId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @ApiModelProperty(value = "Date", example = "2020-02-12")
    private LocalDate date;

    @NotNull
    @ApiModelProperty(value = "Confirm", example = "true")
    private Boolean confirm;

    @ReportRecordListExist
    @ApiModelProperty(value = "reportRecord's id", example = "[1]")
    private List<Long> reportRecordId;

    @Min(0)
    @Max(24 * 60)
    @ApiModelProperty(value = "Hour (Hour >= Work unit)", example = "18")
    private Integer hour;

    @Min(0)
    @Max(24 * 60)
    @ApiModelProperty(value = "Work unit(Work unit <= Hour)", example = "15")
    private Integer workUnit;

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

}
