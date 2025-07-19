package com.example.testtasknews.dto.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Data Transfer Object for field-level validation errors.
 * <p>
 * Contains a map of field names to error messages and an HTTP status code.
 */
@Getter
@Setter
public class FieldErrorDto extends ErrorDto {

    private Map<String, String> errors;

    public FieldErrorDto(Map<String, String> errors, int code) {
        super(code);
        this.errors = errors;
    }
}
