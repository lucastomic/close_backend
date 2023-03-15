package com.close.close.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * UserNotFoundAdvice handles the UserNotFoundException along the whole application.
 */
@ControllerAdvice
public class UserNotFoundAdvice {
    /**
     * userNotFoundHandler is triggered every time a UserNotFoundException
     * is throw in any part of the application. It returns the exception's
     * message as a response with thte 404 (not found) status.
     * @param ex is the exception
     * @return String with the exception message in response format
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    String userNotFoundHandler(UserNotFoundException ex){
        return ex.getMessage();
    }
}
