package com.example.testtasknews.repository;

import com.example.testtasknews.model.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<News, Long> {

    @EntityGraph(attributePaths = {"comments"})
    Page<News> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"comments"})
    Optional<News> findById(Long id);

}
