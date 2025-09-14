package com.example.testtasknews.repository;

import com.example.testtasknews.model.News;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NewsRepositoryTests {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    void testFindById_ShouldReturnNewsWithComments() {
        Optional<News> newsOpt = newsRepository.findById(1L);
        assertThat(newsOpt).isPresent();
        News news = newsOpt.get();
        assertThat(news.getTitle()).isEqualTo("News 1");
        assertThat(news.getComments()).isNotEmpty();
    }

    @Test
    void testFindAll_ShouldReturnPagedNews() {
        var page = newsRepository.findAll(org.springframework.data.domain.PageRequest.of(0, 10));
        assertThat(page).isNotEmpty();
        assertThat(page.getContent()).hasSizeGreaterThanOrEqualTo(1);
    }
}
