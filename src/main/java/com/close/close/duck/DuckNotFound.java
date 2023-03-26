package com.close.close.duck;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

public class DuckNotFound extends StatusException {
    private final HttpStatus STATUS = HttpStatus.NOT_FOUND;


    /**
     * Constructs a new DuckNotFound exception with the given senderId and receiverId.
     * @param senderId the senderId of the missing Duck object
     * @param receiverId the receiverId of the missing Duck object
     */
    public DuckNotFound(Long senderId, Long receiverId) {
        super(String.format("Duck object not found for senderId=%d and receiverId=%d", senderId, receiverId));
    }

    /**
     * Overrides the getStatus() method to return a NOT_FOUND HttpStatus.
     * @return the NOT_FOUND HttpStatus
     */
    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }

}
