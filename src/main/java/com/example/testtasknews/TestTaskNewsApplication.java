package com.example.testtasknews;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

/**
 * Main class for the News REST API application.
 * <p>
 * Boots the Spring Boot application, enables auditing, caching, and pagination support.
 * Entry point for the news service backend.
 */
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableCaching
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class TestTaskNewsApplication {

    /**
     * Application entry point.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(TestTaskNewsApplication.class, args);
    }

}
