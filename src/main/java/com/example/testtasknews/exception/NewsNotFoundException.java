package com.example.testtasknews.exception;

/**
 * Exception thrown when a requested news article is not found in the system.
 */
public class NewsNotFoundException extends EntityNotFoundException {
    public NewsNotFoundException(String message) {
        super(message);
    }
}
