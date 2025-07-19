package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateCommentRequestDto;
import com.example.testtasknews.dto.request.UpdateCommentRequestDto;
import com.example.testtasknews.dto.response.CommentResponseDto;

/**
 * Service interface for managing comments on news articles.
 * <p>
 * Extends the generic Service interface for CRUD operations on comments.
 */
public interface CommentService extends Service<CreateCommentRequestDto, UpdateCommentRequestDto, CommentResponseDto> {
}
