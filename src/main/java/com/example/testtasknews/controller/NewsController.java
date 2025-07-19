package com.example.testtasknews.controller;

import com.example.testtasknews.dto.request.CreateNewsRequestDto;
import com.example.testtasknews.dto.request.UpdateNewsRequestDto;
import com.example.testtasknews.dto.response.NewsResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.service.NewsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing news articles.
 * <p>
 * Provides endpoints for creating, updating, deleting, and retrieving news.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
@SecurityRequirement(name = "BearerAuthentication")
public class NewsController {

    private final NewsService newsService;

    /**
     * Creates a new news article.
     *
     * @param newsRequestDto The request body containing news details.
     * @return A ResponseEntity with status 201 Created.
     */
    @PostMapping
    public ResponseEntity<Void> createNews(@RequestBody @Valid CreateNewsRequestDto newsRequestDto) {
        newsService.save(newsRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * Retrieves all news articles with pagination and sorting.
     *
     * @param page     The page number (0-based).
     * @param pageSize The number of items per page.
     * @param sort     The field to sort by (e.g., "id", "title", "date").
     * @return A ResponseEntity containing a PageResponseDto of NewsResponseDto.
     */
    @GetMapping
    public ResponseEntity<PageResponseDto<NewsResponseDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort)
    {
        PageResponseDto<NewsResponseDto> news = newsService.findAll(PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(news);
    }

    /**
     * Retrieves a single news article by its ID.
     *
     * @param id The ID of the news article to retrieve.
     * @return A ResponseEntity containing the NewsResponseDto.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDto> findById(@PathVariable Long id) {
        NewsResponseDto newsResponseDto = newsService.findById(id);
        return ResponseEntity.ok(newsResponseDto);
    }

    /**
     * Updates an existing news article.
     *
     * @param newsRequestDto The request body containing updated news details.
     * @return A ResponseEntity with status 204 No Content.
     */
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateNewsRequestDto newsRequestDto) {
        newsService.update(newsRequestDto);
        return ResponseEntity.noContent().build();
    }

    /**
     * Deletes a news article by its ID.
     *
     * @param id The ID of the news article to delete.
     * @return A ResponseEntity with status 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
