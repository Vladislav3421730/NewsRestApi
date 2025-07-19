package com.example.testtasknews.exception;

/**
 * Exception thrown when user registration fails due to invalid data or duplicate user.
 */
public class RegistrationFailedException extends RuntimeException {
    public RegistrationFailedException(String message) {
        super(message);
    }
}
