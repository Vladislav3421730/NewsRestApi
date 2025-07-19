package com.example.testtasknews.dto.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Data Transfer Object for updating news articles.
 * <p>
 * Contains information required to update an existing news entry.
 */
@Data
public class UpdateNewsRequestDto {

    @NotNull(message = "id must be not null")
    @Min(value = 1, message = "id must be more than 0")
    private Long id;

    @NotBlank(message = "title must be not blank")
    private String title;

    @NotNull(message = "text must be not null")
    private String text;
}
