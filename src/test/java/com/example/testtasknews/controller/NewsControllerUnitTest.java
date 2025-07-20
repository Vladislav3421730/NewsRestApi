package com.example.testtasknews.controller;

import com.example.testtasknews.dto.request.CreateNewsRequestDto;
import com.example.testtasknews.dto.request.UpdateNewsRequestDto;
import com.example.testtasknews.dto.response.NewsResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.exception.GlobalExceptionHandler;
import com.example.testtasknews.exception.NewsNotFoundException;
import com.example.testtasknews.service.NewsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class NewsControllerUnitTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static NewsService newsService;
    private static MockMvc mockMvc;

    private static CreateNewsRequestDto validCreateNewsRequest;
    private static CreateNewsRequestDto invalidCreateNewsRequestEmptyTitle;
    private static CreateNewsRequestDto invalidCreateNewsRequestEmptyText;
    private static UpdateNewsRequestDto validUpdateNewsRequest;
    private static UpdateNewsRequestDto invalidUpdateNewsRequestEmptyTitle;
    private static NewsResponseDto newsResponseDto;
    private static PageResponseDto<NewsResponseDto> pageResponseDto;
    private static String invalidCreateNewsJson;
    private static String invalidUpdateNewsJson;

    @BeforeEach
    void setup() {
        newsService = Mockito.mock(NewsService.class);
        NewsController newsController = new NewsController(newsService);
        mockMvc = MockMvcBuilders.standaloneSetup(newsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        validCreateNewsRequest = new CreateNewsRequestDto();
        validCreateNewsRequest.setTitle("Test News");
        validCreateNewsRequest.setText("Some text");

        invalidCreateNewsRequestEmptyTitle = new CreateNewsRequestDto();
        invalidCreateNewsRequestEmptyTitle.setTitle("");
        invalidCreateNewsRequestEmptyTitle.setText("Some text");

        invalidCreateNewsRequestEmptyText = new CreateNewsRequestDto();
        invalidCreateNewsRequestEmptyText.setTitle("");
        invalidCreateNewsRequestEmptyText.setText("");

        validUpdateNewsRequest = new UpdateNewsRequestDto();
        validUpdateNewsRequest.setId(1L);
        validUpdateNewsRequest.setTitle("Updated Title");
        validUpdateNewsRequest.setText("Updated text");

        invalidUpdateNewsRequestEmptyTitle = new UpdateNewsRequestDto();
        invalidUpdateNewsRequestEmptyTitle.setId(1L);
        invalidUpdateNewsRequestEmptyTitle.setTitle("");
        invalidUpdateNewsRequestEmptyTitle.setText("Updated text");

        newsResponseDto = new NewsResponseDto();
        newsResponseDto.setId(1L);
        newsResponseDto.setTitle("Test News");
        newsResponseDto.setText("Some text");

        pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setContent(List.of(newsResponseDto));
        pageResponseDto.setPageNumber(0);
        pageResponseDto.setPageSize(10);
        pageResponseDto.setTotalElements(1L);

        invalidCreateNewsJson = "{\"title\": , \"text\": \"Some text\"}";
        invalidUpdateNewsJson = "{\"id\": , \"title\": \"Updated Title\", \"text\": 123}";
    }

    @Test
    void shouldCreateNews_whenValidRequest() throws Exception {
        doNothing().when(newsService).save(validCreateNewsRequest);
        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validCreateNewsRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequest_whenCreateNewsWithEmptyTitle() throws Exception {
        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidCreateNewsRequestEmptyTitle)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateNewsWithEmptyText() throws Exception {
        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidCreateNewsRequestEmptyText)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateNewsWithInvalidJson() throws Exception {
        mockMvc.perform(post("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(invalidCreateNewsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAllNews_whenFindAll() throws Exception {
        when(newsService.findAll(any(PageRequest.class))).thenReturn(pageResponseDto);
        mockMvc.perform(get("/api/v1/news?page=0&pageSize=10&sort=id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(newsResponseDto.getId()));
    }

    @Test
    void shouldReturnNewsById_whenExists() throws Exception {
        when(newsService.findById(1L)).thenReturn(newsResponseDto);
        mockMvc.perform(get("/api/v1/news/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(newsResponseDto.getId()));
    }

    @Test
    void shouldReturnNotFound_whenNewsByIdNotExists() throws Exception {
        when(newsService.findById(2L)).thenThrow(new NewsNotFoundException("Not found"));
        mockMvc.perform(get("/api/v1/news/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateNews_whenValidRequest() throws Exception {
        doNothing().when(newsService).update(validUpdateNewsRequest);
        mockMvc.perform(put("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validUpdateNewsRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateNewsWithEmptyTitle() throws Exception {
        mockMvc.perform(put("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidUpdateNewsRequestEmptyTitle)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateNewsWithInvalidJson() throws Exception {
        mockMvc.perform(put("/api/v1/news")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(invalidUpdateNewsJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteNews_whenExists() throws Exception {
        doNothing().when(newsService).deleteById(1L);
        mockMvc.perform(delete("/api/v1/news/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFound_whenDeleteNewsNotExists() throws Exception {
        Mockito.doThrow(new NewsNotFoundException("Not found")).when(newsService).deleteById(2L);
        mockMvc.perform(delete("/api/v1/news/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
