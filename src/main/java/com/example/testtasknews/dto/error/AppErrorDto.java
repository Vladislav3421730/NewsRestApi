package com.example.testtasknews.dto.error;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for application-level error responses.
 * <p>
 * Contains error message and HTTP status code for API error handling.
 */
@Getter
@Setter
public class AppErrorDto extends ErrorDto {

    private String message;

    public AppErrorDto(String message, int code) {
        super(code);
        this.message = message;
    }
}
