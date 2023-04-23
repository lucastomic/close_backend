package com.close.close.interest;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

/**
 * InterestNotFoundException indicates that no interest have been found with the specifications given
 * It's linked to a 404 not found status
 */
public class InterestNotFoundException extends StatusException {
    private final HttpStatus STATUS = HttpStatus.NOT_FOUND;

    public InterestNotFoundException(){
        super("No interest found");
    }

    @Override
    public HttpStatus getStatus() {
        return STATUS;
    }
}
