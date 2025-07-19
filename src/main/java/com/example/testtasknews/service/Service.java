package com.example.testtasknews.service;

import com.example.testtasknews.dto.response.PageResponseDto;
import org.springframework.data.domain.PageRequest;

public interface Service<C, U, V> {

    void save(C createRequestDto);

    PageResponseDto<V> findAll(PageRequest pageRequest);

    V findById(Long id);

    void deleteById(Long id);

    void update(U updateRequestDto);
}
