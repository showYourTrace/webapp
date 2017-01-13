package com.showyourtrace.exception;

public class NotFoundException extends WebApplicationException {

    public NotFoundException() {
        super(404);
    }

    public NotFoundException(String message) {
        super(404, message);
    }

}
