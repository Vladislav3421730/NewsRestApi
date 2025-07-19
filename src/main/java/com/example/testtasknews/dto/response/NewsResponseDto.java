package com.example.testtasknews.dto.response;

import com.example.testtasknews.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class NewsResponseDto {

    private Long id;
    private String title;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN)
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN)
    private LocalDateTime lastEditDate;
    private Long insertedById;
    private Long updatedById;
    private List<CommentResponseDto> comments = new ArrayList<>();

}
