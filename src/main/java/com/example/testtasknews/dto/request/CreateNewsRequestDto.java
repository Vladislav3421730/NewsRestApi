package com.example.testtasknews.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateNewsRequestDto {

    @NotBlank(message = "title must be not blank")
    private String title;

    @NotNull(message = "text must be not null")
    private String text;

}
