package com.close.close.user;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

public class UsernameDuplicatedException extends StatusException {
    private final HttpStatus STATUS = HttpStatus.CONFLICT;
    public UsernameDuplicatedException(String username){
        super("Already exists a user with the username " + username);
    }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
