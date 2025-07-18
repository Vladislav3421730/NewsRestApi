package com.example.testtasknews.dto.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppErrorDto extends ErrorDto {

    private String message;

    public AppErrorDto(String message, int code) {
        super(code);
        this.message = message;
    }
}
