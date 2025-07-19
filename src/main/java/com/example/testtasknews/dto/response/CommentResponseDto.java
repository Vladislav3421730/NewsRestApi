package com.example.testtasknews.dto.response;

import com.example.testtasknews.utils.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for comment responses.
 * <p>
 * Contains information about a comment returned to the client.
 */
@Data
public class CommentResponseDto {

    private Long id;
    private String text;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN)
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constants.TIMESTAMP_PATTERN)
    private LocalDateTime lastEditDate;
    private Long insertedById;
    private Long newsId;
}
