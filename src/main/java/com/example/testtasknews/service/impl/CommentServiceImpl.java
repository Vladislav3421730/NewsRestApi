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
import org.hibernate.tool.schema.spi.CommandAcceptanceException;
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
    public PageResponseDto<CommentResponseDto> findAll(PageRequest pageRequest) {
        Page<CommentResponseDto> comments = commentRepository.findAll(pageRequest)
                .map(commentMapper::toDto);
        return PageMapper.toDto(comments);

    }

    @Override
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
