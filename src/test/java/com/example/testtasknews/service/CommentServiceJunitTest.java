package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateCommentRequestDto;
import com.example.testtasknews.dto.request.UpdateCommentRequestDto;
import com.example.testtasknews.dto.response.CommentResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.exception.AccessDeniedException;
import com.example.testtasknews.exception.CommentNotFoundException;
import com.example.testtasknews.exception.NewsNotFoundException;
import com.example.testtasknews.mapper.CommentMapper;
import com.example.testtasknews.model.Comment;
import com.example.testtasknews.model.News;
import com.example.testtasknews.repository.CommentRepository;
import com.example.testtasknews.repository.NewsRepository;
import com.example.testtasknews.service.impl.CommentServiceImpl;
import com.example.testtasknews.utils.wrapper.CustomUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CommentServiceJunitTest {
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @InjectMocks
    private CommentServiceImpl commentService;

    private static CreateCommentRequestDto validCreateCommentRequest;
    private static CreateCommentRequestDto invalidCreateCommentRequest;
    private static UpdateCommentRequestDto validUpdateCommentRequest;
    private static UpdateCommentRequestDto invalidUpdateCommentRequest;
    private static Comment comment;
    private static CommentResponseDto commentResponseDto;
    private static PageRequest pageRequest;
    private static Page<Comment> commentPage;
    private static Page<CommentResponseDto> commentResponsePage;
    private static final String ROLE = "ROLE_JOURNALIST";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validCreateCommentRequest = new CreateCommentRequestDto();
        validCreateCommentRequest.setText("Test comment");
        validCreateCommentRequest.setNewId(1L);

        invalidCreateCommentRequest = new CreateCommentRequestDto();
        invalidCreateCommentRequest.setText("Test comment");
        invalidCreateCommentRequest.setNewId(99L);

        validUpdateCommentRequest = new UpdateCommentRequestDto();
        validUpdateCommentRequest.setId(1L);
        validUpdateCommentRequest.setText("Updated comment");

        invalidUpdateCommentRequest = new UpdateCommentRequestDto();
        invalidUpdateCommentRequest.setId(99L);
        invalidUpdateCommentRequest.setText("Updated comment");

        comment = Comment.builder().id(1L).text("Test comment").insertedById(1L).news(News.builder().id(1L).build()).build();
        commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(1L);
        commentResponseDto.setText("Test comment");
        commentResponseDto.setNewsId(1L);

        pageRequest = PageRequest.of(0, 10);
        commentPage = new PageImpl<>(List.of(comment), pageRequest, 1);
        commentResponsePage = new PageImpl<>(List.of(commentResponseDto), pageRequest, 1);
    }

    @Test
    void shouldSaveComment_whenValidRequest() {
        when(newsRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        commentService.save(validCreateCommentRequest);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void shouldThrowNewsNotFoundException_whenSaveCommentToNonexistentNews() {
        when(newsRepository.existsById(99L)).thenReturn(false);
        assertThrows(NewsNotFoundException.class, () -> commentService.save(invalidCreateCommentRequest));
    }

    @Test
    void shouldReturnAllComments_whenFindAll() {
        when(commentRepository.findAll(any(PageRequest.class))).thenReturn(commentPage);
        when(commentMapper.toDto(comment)).thenReturn(commentResponseDto);
        PageResponseDto<CommentResponseDto> result = commentService.findAll(pageRequest);
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getId()).isEqualTo(comment.getId());
    }

    @Test
    void shouldReturnCommentById_whenExists() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(commentResponseDto);
        CommentResponseDto result = commentService.findById(1L);
        assertThat(result.getId()).isEqualTo(comment.getId());
    }

    @Test
    void shouldThrowCommentNotFoundException_whenCommentByIdNotExists() {
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.findById(99L));
    }

    @Test
    void shouldUpdateComment_whenValidRequest() {
        Comment commentToUpdate = Comment.builder().id(1L).text("Old comment").insertedById(1L).build();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(commentToUpdate));
        when(commentRepository.save(any(Comment.class))).thenReturn(commentToUpdate);

        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        @SuppressWarnings("rawtypes")
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE));
        when(authentication.getAuthorities()).thenReturn(authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        commentService.update(validUpdateCommentRequest);
        verify(commentRepository).save(any(Comment.class));
    }

    @Test
    void shouldThrowCommentNotFoundException_whenUpdateCommentNotExists() {
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.update(invalidUpdateCommentRequest));
    }

    @Test
    void shouldThrowAccessDeniedException_whenUpdateCommentWithoutPermission() {
        Comment commentToUpdate = Comment.builder().id(1L).text("Old comment").insertedById(99L).build();
        when(commentRepository.findById(1L)).thenReturn(Optional.of(commentToUpdate));

        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        @SuppressWarnings("rawtypes")
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE));
        when(authentication.getAuthorities()).thenReturn(authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        validUpdateCommentRequest.setId(1L);
        assertThrows(AccessDeniedException.class, () -> commentService.update(validUpdateCommentRequest));
    }

    @Test
    void shouldDeleteComment_whenExists() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));
        doNothing().when(commentRepository).deleteById(1L);

        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        @SuppressWarnings("rawtypes")
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE));
        when(authentication.getAuthorities()).thenReturn(authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        commentService.deleteById(1L);
        verify(commentRepository).deleteById(1L);
    }

    @Test
    void shouldThrowCommentNotFoundException_whenDeleteCommentNotExists() {
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CommentNotFoundException.class, () -> commentService.deleteById(99L));
    }

    @Test
    void shouldThrowAccessDeniedException_whenDeleteCommentWithoutPermission() {
        Comment commentToDelete = Comment.builder().id(1L).text("Old comment").insertedById(99L).build();

        when(commentRepository.findById(1L)).thenReturn(Optional.of(commentToDelete));

        Authentication authentication = mock(Authentication.class);
        CustomUserDetails userDetails = mock(CustomUserDetails.class);
        when(userDetails.getId()).thenReturn(1L);
        when(authentication.getPrincipal()).thenReturn(userDetails);

        @SuppressWarnings("rawtypes")
        Set authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(ROLE));
        when(authentication.getAuthorities()).thenReturn(authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        assertThrows(AccessDeniedException.class, () -> commentService.deleteById(1L));
    }
}
