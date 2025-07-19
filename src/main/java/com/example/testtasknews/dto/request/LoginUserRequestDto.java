package com.example.testtasknews.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object for user login requests.
 * <p>
 * Contains credentials required for user authentication.
 */
@Data
public class LoginUserRequestDto {

    @NotBlank(message = "Username must be not null")
    private String username;

    @NotBlank(message = "Password must be not null")
    private String password;

}
