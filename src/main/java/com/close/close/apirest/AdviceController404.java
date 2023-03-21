package com.close.close.apirest;

import com.close.close.duck.DuckToItselfException;
import com.close.close.interest.Interest;
import com.close.close.interest.InterestNotFoundException;
import com.close.close.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * AdviceController404 handles all the 404 status code (not found) exceptions.
 * It's triggered every time an exception which needs a 404 status is thrown all over the application.
 */
//TODO: make it more general
@ControllerAdvice
public class AdviceController404 {
    /**
     * userNotFoundHandler is triggered every time a UserNotFoundException
     * is throw in any part of the application. It returns the exception's
     * message as a response with thte 404 (not found) status.
     * @param ex is the exception
     * @return String with the exception message in response format
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UserNotFoundException.class, InterestNotFoundException.class})
    String userNotFoundHandler(RuntimeException ex){
        return ex.getMessage();
    }
}
