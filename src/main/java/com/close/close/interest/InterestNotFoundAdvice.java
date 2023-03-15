package com.close.close.interest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * InterestNotFoundAdvice handles the InterestNotFound exceptions
 */
@ControllerAdvice
public class InterestNotFoundAdvice {
    /**
     * exceptionHandler is triggered every time a InterestNotFoundException is
     * thrown. It returns the exception message with a 404 (not found) status code.
     * @param ex exception throwed
     * @return exception message with 404 status
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InterestNotFoundException.class)
    String exceptionHandler(InterestNotFoundException ex){
        return ex.getMessage();
    }
}
