package com.example.testtasknews.controller;

import com.example.testtasknews.dto.request.CreateCommentRequestDto;
import com.example.testtasknews.dto.request.UpdateCommentRequestDto;
import com.example.testtasknews.dto.response.CommentResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.exception.CommentNotFoundException;
import com.example.testtasknews.exception.GlobalExceptionHandler;
import com.example.testtasknews.service.CommentService;
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

class CommentControllerUnitTest {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static CommentService commentService;
    private static MockMvc mockMvc;

    private static CreateCommentRequestDto validCreateCommentRequest;
    private static CreateCommentRequestDto invalidCreateCommentRequestEmptyText;
    private static CreateCommentRequestDto invalidCreateCommentRequestNullNewsId;
    private static UpdateCommentRequestDto validUpdateCommentRequest;
    private static UpdateCommentRequestDto invalidUpdateCommentRequestEmptyText;
    private static UpdateCommentRequestDto invalidUpdateCommentRequestNullId;
    private static CommentResponseDto commentResponseDto;
    private static PageResponseDto<CommentResponseDto> pageResponseDto;
    private static String invalidCreateCommentJson;
    private static String invalidUpdateCommentJson;

    @BeforeEach
    void setup() {
        commentService = Mockito.mock(CommentService.class);
        CommentController commentController = new CommentController(commentService);
        mockMvc = MockMvcBuilders.standaloneSetup(commentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        validCreateCommentRequest = new CreateCommentRequestDto();
        validCreateCommentRequest.setText("Test comment");
        validCreateCommentRequest.setNewId(1L);

        invalidCreateCommentRequestEmptyText = new CreateCommentRequestDto();
        invalidCreateCommentRequestEmptyText.setText("");
        invalidCreateCommentRequestEmptyText.setNewId(1L);

        invalidCreateCommentRequestNullNewsId = new CreateCommentRequestDto();
        invalidCreateCommentRequestNullNewsId.setText("Test comment");
        invalidCreateCommentRequestNullNewsId.setNewId(null);

        validUpdateCommentRequest = new UpdateCommentRequestDto();
        validUpdateCommentRequest.setId(1L);
        validUpdateCommentRequest.setText("Updated comment");

        invalidUpdateCommentRequestEmptyText = new UpdateCommentRequestDto();
        invalidUpdateCommentRequestEmptyText.setId(1L);
        invalidUpdateCommentRequestEmptyText.setText("");

        invalidUpdateCommentRequestNullId = new UpdateCommentRequestDto();
        invalidUpdateCommentRequestNullId.setId(null);
        invalidUpdateCommentRequestNullId.setText("Updated comment");

        commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(1L);
        commentResponseDto.setText("Test comment");
        commentResponseDto.setNewsId(1L);

        pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setContent(List.of(commentResponseDto));
        pageResponseDto.setPageNumber(0);
        pageResponseDto.setPageSize(10);
        pageResponseDto.setTotalElements(1L);

        invalidCreateCommentJson = "{\"text\": , \"newId\": 1}";
        invalidUpdateCommentJson = "{\"id\": , \"text\": 123}";
    }

    @Test
    void shouldCreateComment_whenValidRequest() throws Exception {
        doNothing().when(commentService).save(validCreateCommentRequest);
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validCreateCommentRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequest_whenCreateCommentWithEmptyText() throws Exception {
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidCreateCommentRequestEmptyText)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateCommentWithNullNewsId() throws Exception {
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidCreateCommentRequestNullNewsId)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenCreateCommentWithInvalidJson() throws Exception {
        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(invalidCreateCommentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnAllComments_whenFindAll() throws Exception {
        when(commentService.findAll(any(PageRequest.class))).thenReturn(pageResponseDto);
        mockMvc.perform(get("/api/v1/comment?page=0&pageSize=10&sort=id")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(commentResponseDto.getId()));
    }

    @Test
    void shouldReturnCommentById_whenExists() throws Exception {
        when(commentService.findById(1L)).thenReturn(commentResponseDto);
        mockMvc.perform(get("/api/v1/comment/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(commentResponseDto.getId()));
    }

    @Test
    void shouldReturnNotFound_whenCommentByIdNotExists() throws Exception {
        when(commentService.findById(2L)).thenThrow(new CommentNotFoundException("Not found"));
        mockMvc.perform(get("/api/v1/comment/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateComment_whenValidRequest() throws Exception {
        doNothing().when(commentService).update(validUpdateCommentRequest);
        mockMvc.perform(put("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(validUpdateCommentRequest)))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateCommentWithEmptyText() throws Exception {
        mockMvc.perform(put("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidUpdateCommentRequestEmptyText)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateCommentWithNullId() throws Exception {
        mockMvc.perform(put("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(objectMapper.writeValueAsString(invalidUpdateCommentRequestNullId)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenUpdateCommentWithInvalidJson() throws Exception {
        mockMvc.perform(put("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(invalidUpdateCommentJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteComment_whenExists() throws Exception {
        doNothing().when(commentService).deleteById(1L);
        mockMvc.perform(delete("/api/v1/comment/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFound_whenDeleteCommentNotExists() throws Exception {
        Mockito.doThrow(new CommentNotFoundException("Not found")).when(commentService).deleteById(2L);
        mockMvc.perform(delete("/api/v1/comment/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
