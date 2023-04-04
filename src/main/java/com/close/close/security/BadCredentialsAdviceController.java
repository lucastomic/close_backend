package com.close.close.security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles the BadCredentialsException
 */
@ControllerAdvice
public class BadCredentialsAdviceController {
    /**
     * Handles the exception
     * @param ex exception to handle
     * @return bad request status code with the exception's details in the body
     */
    @ExceptionHandler(BadCredentialsException.class)
    ResponseEntity<String> handleFunction(BadCredentialsException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
