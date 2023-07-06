package com.close.close.user.dto;

import com.close.close.apirest.StatusException;
import org.springframework.http.HttpStatus;

public class DTOParsingException extends StatusException {
    public DTOParsingException() {
        super("Could not show object properly");
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
