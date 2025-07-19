package com.example.testtasknews.mapper;

import com.example.testtasknews.dto.response.PageResponseDto;
import org.springframework.data.domain.Page;

public class PageMapper {

    public static <T> PageResponseDto<T> toDto(Page<T> page) {
        return PageResponseDto.<T>builder()
                .content(page.getContent())
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .build();
    }
}
