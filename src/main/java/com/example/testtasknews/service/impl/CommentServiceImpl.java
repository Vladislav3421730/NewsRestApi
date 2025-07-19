package com.example.testtasknews.service.impl;

import com.example.testtasknews.dto.request.CreateCommentRequestDto;
import com.example.testtasknews.dto.request.UpdateCommentRequestDto;
import com.example.testtasknews.dto.response.CommentResponseDto;
import com.example.testtasknews.dto.response.PageResponseDto;

import com.example.testtasknews.exception.CommentNotFoundException;
import com.example.testtasknews.exception.NewsNotFoundException;
import com.example.testtasknews.mapper.CommentMapper;
import com.example.testtasknews.mapper.PageMapper;
import com.example.testtasknews.model.Comment;
import com.example.testtasknews.model.News;
import com.example.testtasknews.repository.CommentRepository;
import com.example.testtasknews.repository.NewsRepository;
import com.example.testtasknews.service.CommentService;
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
public class CommentServiceImpl implements CommentService {

    private final NewsRepository newsRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "comment_list", allEntries = true),
            @CacheEvict(value = "news_item", key = "'id_'+#createRequestDto.newId")
    })
    public void save(CreateCommentRequestDto createRequestDto) {
        if (!newsRepository.existsById(createRequestDto.getNewId())) {
            log.error("News with id {} wasn't found", createRequestDto.getNewId());
            throw new NewsNotFoundException("News with id " + createRequestDto.getNewId() + " wasn't found");
        }

        Comment comment = Comment.builder()
                .text(createRequestDto.getText())
                .news(News.builder().id(createRequestDto.getNewId()).build())
                .build();

        commentRepository.save(comment);
        log.info("Comment {} with id {} was stored", comment.getText(), comment.getId());
    }

    @Override
    @Cacheable(value = "comment_list", key = "'page_'+#pageRequest.pageNumber+'_'+#pageRequest.pageSize+'_'+#pageRequest.sort")
    public PageResponseDto<CommentResponseDto> findAll(PageRequest pageRequest) {
        Page<CommentResponseDto> comments = commentRepository.findAll(pageRequest)
                .map(commentMapper::toDto);
        return PageMapper.toDto(comments);

    }

    @Override
    @Cacheable(value = "comment_item", key = "'id_'+#id")
    public CommentResponseDto findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::toDto)
                .orElseThrow(() -> {
                    log.error("Comment with id {} wasn't found", id);
                    throw new CommentNotFoundException("Comment with id " + id + " wasn't found");
                });
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "comment_item", key = "'id_'+#id"),
            @CacheEvict(value = "comment_list", allEntries = true)
    })
    public void deleteById(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> {
            log.error("Comment with id {} wasn't found", id);
            throw new CommentNotFoundException("Comment with id " + id + " wasn't found");
        });
        CheckPrivacy.checkPrivacy(comment.getInsertedById());
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(put = {
            @CachePut(value = "comment_item", key = "'id_'+#updateRequestDto.id")
    }, evict = {
            @CacheEvict(value = "comment_list", allEntries = true)
    })
    public void update(UpdateCommentRequestDto updateRequestDto) {
        Comment comment = commentRepository.findById(updateRequestDto.getId()).orElseThrow(() -> {
            log.error("Comment with id {} wasn't found", updateRequestDto.getId());
            throw new CommentNotFoundException("Comment with id " + updateRequestDto.getId() + " wasn't found");
        });
        CheckPrivacy.checkPrivacy(comment.getInsertedById());
        comment.setText(updateRequestDto.getText());
        commentRepository.save(comment);
    }
}
