package com.close.close.duck;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

/**
 * DuckToItselfException is thrown when a user sends a duck to itself.
 * This is, when is tried to save a Duck entity with the same receiver and sender ID.
 * This exception is linked to a BAD REQUEST status
 */

public class DuckToItselfException extends StatusException{
   private final HttpStatus STATUS = HttpStatus.BAD_REQUEST;
   DuckToItselfException(Long id){
       super("A user can't send a duck to itself. ID:" + id);
   }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
