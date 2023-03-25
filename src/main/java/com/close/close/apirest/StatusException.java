package com.close.close.apirest;

import org.springframework.http.HttpStatus;

/**
 * StatusException are those exceptions which has an intrinsic
 * status code. Such as, exceptions which implies a 404 NOT FOUND code.
 */
abstract public class StatusException extends RuntimeException{
    public StatusException(String message){
        super(message);
    }

    /**
     * getStatus returns the exception's status code
     * @return HttpStatus object with the status code
     */
    abstract public HttpStatus getStatus();

}
