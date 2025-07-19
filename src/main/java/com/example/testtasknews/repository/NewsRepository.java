package com.example.testtasknews.repository;

import com.example.testtasknews.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
