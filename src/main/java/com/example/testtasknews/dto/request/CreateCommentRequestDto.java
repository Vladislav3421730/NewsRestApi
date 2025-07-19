package com.example.testtasknews.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequestDto {

    @NotBlank(message = "text must be not null")
    private String text;
    @NotNull(message = "newId must be not null")
    @Min(value = 1, message = "newId must be more than 0")
    private Long newId;
}
