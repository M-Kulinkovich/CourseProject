package com.itlab.group3.security.dto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AuthenticationRequestDto {

    @ApiModelProperty(example = "white_wolf@witcher.net")
    private String email;

    @ApiModelProperty(example = "secret")
    private String password;
}
