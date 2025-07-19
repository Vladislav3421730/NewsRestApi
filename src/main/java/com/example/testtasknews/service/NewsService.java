package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateNewsRequestDto;
import com.example.testtasknews.dto.request.UpdateNewsRequestDto;
import com.example.testtasknews.dto.response.NewsResponseDto;

public interface NewsService extends Service<CreateNewsRequestDto, UpdateNewsRequestDto, NewsResponseDto> {

}
