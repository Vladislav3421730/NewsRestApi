package com.example.testtasknews.mapper;

import com.example.testtasknews.dto.response.CommentResponseDto;
import com.example.testtasknews.model.Comment;
import com.example.testtasknews.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    @Mapping(source = "news", target = "newsId")
    CommentResponseDto toDto(Comment comment);

    default Long mapNewsToDto(News news) {
        return news == null ? null : news.getId();
    }
}
