package com.close.close.duck;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

public class DuckNotFound extends StatusException {
    private final HttpStatus STATUS = HttpStatus.NOT_FOUND;


    public DuckNotFound(Long senderId, Long receiverId) {
        super(String.format("Duck object not found for senderId=%d and receiverId=%d", senderId, receiverId));
    }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
