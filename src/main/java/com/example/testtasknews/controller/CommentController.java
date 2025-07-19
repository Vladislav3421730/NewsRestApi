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

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
@SecurityRequirement(name = "BearerAuthentication")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createNews(@RequestBody @Valid CreateCommentRequestDto commentRequestDto) {
        commentService.save(commentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<CommentResponseDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort)
    {
        PageResponseDto<CommentResponseDto> news = commentService.findAll(PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommentResponseDto> findById(@PathVariable Long id) {
        CommentResponseDto commentResponseDto = commentService.findById(id);
        return ResponseEntity.ok(commentResponseDto);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateCommentRequestDto updateCommentRequestDto) {
        commentService.update(updateCommentRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
