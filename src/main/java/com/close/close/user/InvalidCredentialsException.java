package com.close.close.user;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

/**
 * InvalidCredentialsException is thrown when is tried to sign in with invalid credentials.
 * This could be either wrong password or a wrong phone number.
 * It's associated to the 400 BAD REQUEST status code.
 */
public class InvalidCredentialsException extends StatusException {
    public InvalidCredentialsException(){
        super("Password or phone number are invalid");
    }
    private final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
