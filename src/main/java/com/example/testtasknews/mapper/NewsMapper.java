package com.example.testtasknews.mapper;

import com.example.testtasknews.dto.response.CommentResponseDto;
import com.example.testtasknews.dto.response.NewsResponseDto;
import com.example.testtasknews.model.Comment;
import com.example.testtasknews.model.News;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NewsMapper {

    CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    NewsResponseDto toDto(News news);

    default List<CommentResponseDto> mapFromNewsToNewsDto(List<Comment> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList());
    }

}
