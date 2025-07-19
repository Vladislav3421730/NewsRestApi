package com.example.testtasknews.exception;

import com.example.testtasknews.dto.error.AppErrorDto;
import com.example.testtasknews.dto.error.FieldErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the REST API.
 * <p>
 * Handles validation errors, entity not found, registration failures, and other application exceptions.
 * Returns appropriate error responses to the client.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for request bodies.
     *
     * @param methodArgumentNotValidException exception containing validation errors
     * @return ResponseEntity with field error details and HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorDto> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(new FieldErrorDto(errors,400), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles entity not found exceptions.
     *
     * @param entityNotFoundException exception indicating the entity was not found
     * @return ResponseEntity with error details and HTTP 404 status
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<AppErrorDto> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return new ResponseEntity<>(new AppErrorDto(entityNotFoundException.getMessage(),404), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles registration failure exceptions.
     *
     * @param registrationFailedException exception indicating registration failed
     * @return ResponseEntity with error details and HTTP 400 status
     */
    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<AppErrorDto> handleRegistrationFailedException(RegistrationFailedException registrationFailedException) {
        return new ResponseEntity<>(new AppErrorDto(registrationFailedException.getMessage(),400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<AppErrorDto> handleLoginFailedException(LoginFailedException loginFailedException) {
        return new ResponseEntity<>(new AppErrorDto(loginFailedException.getMessage(),400), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<AppErrorDto> handleAccessDeniedException(AccessDeniedException accessDeniedException) {
        return new ResponseEntity<>(new AppErrorDto(accessDeniedException.getMessage(), 403), HttpStatus.FORBIDDEN);
    }

}
