package com.close.close.apirest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handles the DataIntegrityViolationException
 */
@ControllerAdvice
public class DataIntegrityViolationAdviceController {

    /**
     * Returns a response entity with a 500 INTERNAL SERVER ERROR status and a message
     * explaining the details of the exception in the body
     * @param ex exception to handle
     * @return ResponseEntity object with a 500 status code and de exception's details on the body
     */
    @ExceptionHandler({
            DataIntegrityViolationException.class
    })
    ResponseEntity<String> handleFunction(DataIntegrityViolationException ex) {
        String message = ex.getMostSpecificCause().getLocalizedMessage();
        return ResponseEntity.internalServerError().body(message);
    }
}
