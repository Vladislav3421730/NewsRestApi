package com.example.testtasknews.exception;

/**
 * Exception thrown when a requested entity is not found in the system.
 */
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
