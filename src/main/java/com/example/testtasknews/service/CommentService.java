package com.example.testtasknews.service;

import com.example.testtasknews.dto.request.CreateCommentRequestDto;
import com.example.testtasknews.dto.request.UpdateCommentRequestDto;
import com.example.testtasknews.dto.response.CommentResponseDto;

public interface CommentService extends Service<CreateCommentRequestDto, UpdateCommentRequestDto, CommentResponseDto> {
}
