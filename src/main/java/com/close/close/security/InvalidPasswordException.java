package com.close.close.security;

import com.close.close.apirest.StatusException;
import com.close.close.user.User;
import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends StatusException {


    public InvalidPasswordException() {
        super("The password must have at least " + User.MINIMUM_PASSWORD_LENGTH + " characters");
    }

    /**
     * getStatus returns the exception's status code
     *
     * @return HttpStatus object with the status code
     */
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
