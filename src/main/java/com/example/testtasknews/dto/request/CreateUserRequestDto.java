package com.example.testtasknews.dto.request;

import com.example.testtasknews.utils.validation.RoleValidation;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for user registration requests.
 * <p>
 * Contains user information required for creating a new user account.
 */
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
