package com.example.testtasknews.exception;

public class NewsNotFoundException extends EntityNotFoundException {
    public NewsNotFoundException(String message) {
        super(message);
    }
}
