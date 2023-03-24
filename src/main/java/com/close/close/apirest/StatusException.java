package com.close.close.apirest;

import org.springframework.http.HttpStatus;

/**
 * StatusException are those exceptions which has an intrinsic
 * status code. Such as, exceptions which implies a 404 NOT FOUND code.
 */
public interface StatusException {
    /**
     * getStatus returns the exception's status code
     * @return HttpStatus object with the status code
     */
    public HttpStatus getStatus();

    /**
     * getMessage returns the exception's message description
     * @return String with the message
     */
    public String getMessage();
}
