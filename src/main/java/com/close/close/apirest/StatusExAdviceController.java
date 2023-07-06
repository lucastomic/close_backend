package com.close.close.apirest;

import com.close.close.duck.DuckNotFoundException;
import com.close.close.duck.DuckToItselfException;
import com.close.close.interest.InterestNotFoundException;
import com.close.close.security.InvalidPasswordException;
import com.close.close.user.UserNotFoundException;
import com.close.close.user.UsernameDuplicatedException;
import com.close.close.user.dto.DTOParsingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * StatusExAdviceController handles exceptions which implies a status code (implement the StatusException interface).
 * It's triggered every time an exception with status code is thrown all over the application.
 */
@ControllerAdvice
public class StatusExAdviceController {
    /**
     * handleFunction is triggered every time an exception to the defined below
     * is thrown in any part of the application. It returns the exception's
     * message as a response with the exception status code.
     * @param ex is the exception
     * @return ReponseEntity with the correspondent information
     */
    @ExceptionHandler({
            InterestNotFoundException.class,
            UserNotFoundException.class,
            DuckToItselfException.class,
            DuckNotFoundException.class,
            UsernameDuplicatedException.class,
            InvalidPasswordException.class,
            DTOParsingException.class,
    })
    ResponseEntity<String> handleFunction(StatusException ex){
        return new ResponseEntity<String>(ex.getMessage(),ex.getStatus());
    }
}
