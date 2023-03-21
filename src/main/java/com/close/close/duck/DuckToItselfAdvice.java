package com.close.close.duck;


import com.close.close.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * DuckToItselfAdvice handles the DuckToItselfException
 * Is triggered every time a DuckToItselfException is thrown
 */
//TODO:REMOVE
@ControllerAdvice
public class DuckToItselfAdvice {
    /**
     * duckToItslefHandler returns the exception message
     * @param ex exception to handle
     * @return String with the response
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuckToItselfException.class)
    String duckToItselfHanlder(DuckToItselfException ex){return ex.getMessage();}
}
