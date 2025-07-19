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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/news")
@SecurityRequirement(name = "BearerAuthentication")
public class NewsController {

    private final NewsService newsService;

    @PostMapping
    public ResponseEntity<Void> createNews(@RequestBody @Valid CreateNewsRequestDto newsRequestDto) {
        newsService.save(newsRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<NewsResponseDto>> findAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sort)
    {
        PageResponseDto<NewsResponseDto> news = newsService.findAll(PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(news);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsResponseDto> findById(@PathVariable Long id) {
        NewsResponseDto newsResponseDto = newsService.findById(id);
        return ResponseEntity.ok(newsResponseDto);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody @Valid UpdateNewsRequestDto newsRequestDto) {
        newsService.update(newsRequestDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        newsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
