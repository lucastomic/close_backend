package com.close.close.user;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown every time a user is looked for and not found.
 * This exception is linked to the 404 NOT FOUND status code.
 */
public class UserNotFoundException extends RuntimeException implements StatusException {
    private final HttpStatus STATUS = HttpStatus.NOT_FOUND;
    public UserNotFoundException(Long id){
        super("Not found user with id " + id);
    }
    public HttpStatus getStatus() {
        return STATUS;
    }
}
