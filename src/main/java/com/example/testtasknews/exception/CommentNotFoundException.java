package com.example.testtasknews.exception;

/**
 * Exception thrown when a requested comment is not found in the system.
 */
public class CommentNotFoundException extends EntityNotFoundException {
    public CommentNotFoundException(String message) {
        super(message);
    }
}
