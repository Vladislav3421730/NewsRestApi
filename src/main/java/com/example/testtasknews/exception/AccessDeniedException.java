package com.example.testtasknews.exception;

/**
 * Exception thrown when a user attempts to access a resource without sufficient permissions.
 */
public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
