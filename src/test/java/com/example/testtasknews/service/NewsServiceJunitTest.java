package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateNewsRequestDto;
import com.example.testtasknews.dto.request.UpdateNewsRequestDto;
import com.example.testtasknews.dto.response.NewsResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.exception.NewsNotFoundException;
import com.example.testtasknews.exception.AccessDeniedException;
import com.example.testtasknews.mapper.NewsMapper;
import com.example.testtasknews.model.News;
import com.example.testtasknews.repository.NewsRepository;
import com.example.testtasknews.service.impl.NewsServiceImpl;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class NewsServiceJunitTest {
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private NewsMapper newsMapper;
    @InjectMocks
    private NewsServiceImpl newsService;

    private static CreateNewsRequestDto validCreateNewsRequest;
    private static UpdateNewsRequestDto validUpdateNewsRequest;
    private static News news;
    private static NewsResponseDto newsResponseDto;
    private static PageRequest pageRequest;
    private static Page<News> newsPage;
    private static Page<NewsResponseDto> newsResponsePage;
    private static News newsToDelete;
    private static News newsToUpdate;
    private static final String ROLE = "ROLE_JOURNALIST";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validCreateNewsRequest = new CreateNewsRequestDto();
        validCreateNewsRequest.setTitle("Test News");
        validCreateNewsRequest.setText("Some text");

        validUpdateNewsRequest = new UpdateNewsRequestDto();
        validUpdateNewsRequest.setId(1L);
        validUpdateNewsRequest.setTitle("Updated Title");
        validUpdateNewsRequest.setText("Updated text");

        news = News.builder().id(1L).title("Test News").text("Some text").insertedById(1L).build();
        newsToDelete = News.builder().id(1L).title("Old Title").text("Old text").insertedById(99L).build();
        newsToUpdate = News.builder().id(1L).title("Old Title").text("Old text").insertedById(99L).build();

        newsResponseDto = new NewsResponseDto();
        newsResponseDto.setId(1L);
        newsResponseDto.setTitle("Test News");
        newsResponseDto.setText("Some text");

        pageRequest = PageRequest.of(0, 10);
        newsPage = new PageImpl<>(List.of(news), pageRequest, 1);
        newsResponsePage = new PageImpl<>(List.of(newsResponseDto), pageRequest, 1);
    }

    @Test
    void shouldSaveNews_whenValidRequest() {
        when(newsRepository.save(any(News.class))).thenReturn(news);
        newsService.save(validCreateNewsRequest);
        verify(newsRepository).save(any(News.class));
    }

    @Test
    void shouldReturnAllNews_whenFindAll() {
        when(newsRepository.findAll(any(PageRequest.class))).thenReturn(newsPage);
        when(newsMapper.toDto(news)).thenReturn(newsResponseDto);
        PageResponseDto<NewsResponseDto> result = newsService.findAll(pageRequest);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(news.getId());
    }

    @Test
    void shouldReturnNewsById_whenExists() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));
        when(newsMapper.toDto(news)).thenReturn(newsResponseDto);
        NewsResponseDto result = newsService.findById(1L);
        assertThat(result.getId()).isEqualTo(news.getId());
    }

    @Test
    void shouldThrowNewsNotFoundException_whenNewsByIdNotExists() {
        when(newsRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NewsNotFoundException.class, () -> newsService.findById(2L));
    }

    @Test
    void shouldUpdateNews_whenValidRequest() {
        News newsToUpdate = News.builder().id(1L).title("Old Title").text("Old text").insertedById(1L).build();
        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsToUpdate));
        when(newsRepository.save(any(News.class))).thenReturn(newsToUpdate);
        newsService.update(validUpdateNewsRequest);
        verify(newsRepository).save(any(News.class));
    }

    @Test
    void shouldThrowNewsNotFoundException_whenUpdateNewsNotExists() {
        when(newsRepository.findById(2L)).thenReturn(Optional.empty());
        validUpdateNewsRequest.setId(2L);
        assertThrows(NewsNotFoundException.class, () -> newsService.update(validUpdateNewsRequest));
    }

    @Test
    void shouldThrowAccessDeniedException_whenUpdateNewsWithoutPermission() {

        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsToUpdate));

        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        @SuppressWarnings("rawtypes")
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE));
        when(authentication.getAuthorities()).thenReturn(authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        validUpdateNewsRequest.setId(1L);
        assertThrows(AccessDeniedException.class, () -> newsService.update(validUpdateNewsRequest));
    }

    @Test
    void shouldDeleteNews_whenExists() {
        when(newsRepository.findById(1L)).thenReturn(Optional.of(news));
        doNothing().when(newsRepository).deleteById(1L);
        newsService.deleteById(1L);
        verify(newsRepository).deleteById(1L);
    }

    @Test
    void shouldThrowNewsNotFoundException_whenDeleteNewsNotExists() {
        when(newsRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(NewsNotFoundException.class, () -> newsService.deleteById(2L));
    }

    @Test
    void shouldThrowAccessDeniedException_whenDeleteNewsWithoutPermission() {

        when(newsRepository.findById(1L)).thenReturn(Optional.of(newsToDelete));

        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        @SuppressWarnings("rawtypes")
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE));
        when(authentication.getAuthorities()).thenReturn(authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        assertThrows(AccessDeniedException.class, () -> newsService.deleteById(1L));
    }
}
