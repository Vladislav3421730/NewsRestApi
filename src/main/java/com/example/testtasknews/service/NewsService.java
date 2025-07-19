package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateNewsRequestDto;
import com.example.testtasknews.dto.request.UpdateNewsRequestDto;
import com.example.testtasknews.dto.response.NewsResponseDto;

/**
 * Service interface for managing news articles.
 * <p>
 * Extends the generic Service interface for CRUD operations on news.
 */
public interface NewsService extends Service<CreateNewsRequestDto, UpdateNewsRequestDto, NewsResponseDto> {

}
