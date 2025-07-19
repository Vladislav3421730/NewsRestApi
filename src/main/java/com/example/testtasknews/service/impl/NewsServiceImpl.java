package com.example.testtasknews.service.impl;

import com.example.testtasknews.dto.request.CreateNewsRequestDto;
import com.example.testtasknews.dto.request.UpdateNewsRequestDto;
import com.example.testtasknews.dto.response.NewsResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;
import com.example.testtasknews.exception.NewsNotFoundException;
import com.example.testtasknews.mapper.NewsMapper;
import com.example.testtasknews.mapper.PageMapper;
import com.example.testtasknews.model.News;
import com.example.testtasknews.repository.NewsRepository;
import com.example.testtasknews.service.NewsService;
import com.example.testtasknews.utils.CheckPrivacy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    @Override
    @Transactional
    @CacheEvict(value = "news_list", allEntries = true)
    public void save(CreateNewsRequestDto newsRequestDto) {
        News news = News.builder()
                .text(newsRequestDto.getText())
                .title(newsRequestDto.getTitle())
                .build();
        newsRepository.save(news);
        log.info("News {} with id {} was stored", news.getTitle(), news.getId());
    }

    @Override
    @Cacheable(value = "news_list", key = "'page_'+#pageRequest.pageNumber+'_'+#pageRequest.pageSize+'_'+#pageRequest.sort")
    public PageResponseDto<NewsResponseDto> findAll(PageRequest pageRequest) {
        Page<NewsResponseDto> page = newsRepository.findAll(pageRequest)
                .map(newsMapper::toDto);
        return PageMapper.toDto(page);
    }

    @Override
    @Cacheable(value = "news_item", key = "'id_'+#id")
    public NewsResponseDto findById(Long id) {
        return newsRepository.findById(id)
                .map(newsMapper::toDto)
                .orElseThrow(() -> {
                    log.error("News with id {} wasn't found", id);
                    throw new NewsNotFoundException("News with id " + id + " wasn't found");
                });
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "news_item", key = "'id_'+#id"),
            @CacheEvict(value = "news_list", allEntries = true)
    })
    public void deleteById(Long id) {
        News news = newsRepository.findById(id).orElseThrow(() -> {
            log.error("News with id {} wasn't found", id);
            throw new NewsNotFoundException("News with id " + id + " wasn't found");
        });
        CheckPrivacy.checkPrivacy(news.getInsertedById());
        newsRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "news_item", key = "'id_'+#newsRequestDto.id")
    }, evict = {
            @CacheEvict(value = "news_list", allEntries = true)
    })
    public void update(UpdateNewsRequestDto newsRequestDto) {
        News news = newsRepository.findById(newsRequestDto.getId()).orElseThrow(() -> {
            log.error("News with id {} wasn't found", newsRequestDto.getId());
            throw new NewsNotFoundException("News with id " + newsRequestDto.getId() + " wasn't found");
        });
        CheckPrivacy.checkPrivacy(news.getInsertedById());
        news.setTitle(newsRequestDto.getTitle());
        news.setText(newsRequestDto.getText());
        newsRepository.save(news);
    }

}
