package com.kauanallyson.encurtalink.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExpiredDateException extends RuntimeException {
    public ExpiredDateException(String message) {
        super(message);
    }
}
