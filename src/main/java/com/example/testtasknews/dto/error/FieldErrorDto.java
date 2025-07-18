package com.example.testtasknews.dto.error;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FieldErrorDto extends ErrorDto {

    private Map<String, String> errors;

    public FieldErrorDto(Map<String, String> errors, int code) {
        super(code);
        this.errors = errors;
    }
}
