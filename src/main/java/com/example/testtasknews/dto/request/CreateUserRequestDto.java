package com.example.testtasknews.dto.request;

import com.example.testtasknews.utils.validation.RoleValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserRequestDto {

    @NotBlank(message = "username must be not blank")
    private String username;

    @RoleValidation
    private String role;

    @NotBlank(message = "name must be not blank")
    private String name;

    @NotBlank(message = "surname must be not blank")
    private String surname;

    @NotBlank(message = "parent must be not blank")
    private String parentName;

}
