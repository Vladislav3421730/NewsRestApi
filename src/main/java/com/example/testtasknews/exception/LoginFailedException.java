package com.example.testtasknews.exception;

/**
 * Exception thrown when user authentication fails due to invalid credentials.
 */
public class LoginFailedException extends RuntimeException {
    public LoginFailedException(String message) {
        super(message);
    }
}
