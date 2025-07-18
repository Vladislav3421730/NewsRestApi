package com.example.testtasknews.dto.request;

import com.example.testtasknews.validation.Roles;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class CreateUserRequestDto {

    @NotBlank(message = "username must be not blank")
    private String username;

    @Roles
    private List<String> roles;

    @NotBlank(message = "name must be not blank")
    private String name;
    @NotBlank(message = "surname must be not blank")
    private String surname;

    @NotBlank(message = "parent must be not blank")
    private String parentName;

}
