package com.itlab.group3.controllers.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.itlab.group3.dao.model.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "User resource")
public class UserResource extends AbstractResource {

    @ApiModelProperty(value = "Id", example = "1")
    private Long id;

    @NonNull
    @ApiModelProperty(value = "user's name",example = "Ivan")
    private String name;

    @NonNull
    @ApiModelProperty(value = "user's surname",example = "Ivanov")
    private String surname;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @ApiModelProperty(value = "user's password",example = "12345678")
    private String password;

    @NonNull
    @Email
    @ApiModelProperty(value = "user's email",example = "ivan@mail.ru")
    private String email;

    @ApiModelProperty(value = "user's roles", example = "[USER,ADMIN]")
    private List<Role> roles;
}
