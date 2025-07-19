package com.example.testtasknews.controller;

import com.example.testtasknews.dto.request.CreateCommentRequestDto;
import com.example.testtasknews.dto.request.UpdateCommentRequestDto;
import com.example.testtasknews.dto.response.CommentResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing comments on news articles.
 * <p>
 * Provides endpoints for creating, updating, deleting, and retrieving comments.
 */
@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
public class CommentController {

    private final CommentService commentService;

    /**
     * Creates a new comment for a news article.
     *
     * @param commentRequestDto The request body containing comment details.
     * @return ResponseEntity with status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Void> createNews(@RequestBody @Valid CreateCommentRequestDto commentRequestDto) {
        commentService.save(commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves all comments for a news article, paginated and sorted.
     *
     * @param page     The page number (0-based).
     * @param pageSize The number of comments per page.
     * @param sort     The field to sort by (e.g., "id", "createdAt").
     * @return ResponseEntity with a PageResponseDto containing the comments.
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<CommentResponseDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort)
    {
        PageResponseDto<CommentResponseDto> news = commentService.findAll(PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(news);
    }

    /**
     * Retrieves a single comment by its ID.
     *
     * @param id The ID of the comment to retrieve.
     * @return ResponseEntity with the CommentResponseDto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.findById(id);
        return ResponseEntity.ok(commentResponseDto);
    }

    /**
     * Updates an existing comment.
     *
     * @param updateCommentRequestDto The request body containing updated comment details.
     * @return ResponseEntity with status 204 No Content.
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto) {
        commentService.update(updateCommentRequestDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a comment by its ID.
     *
     * @param id The ID of the comment to delete.
     * @return ResponseEntity with status 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
