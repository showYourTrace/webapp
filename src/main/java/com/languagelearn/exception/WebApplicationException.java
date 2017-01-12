package com.languagelearn.exception;

public class WebApplicationException extends RuntimeException {
    private final int status;
    private final String message;

    public WebApplicationException() {
        this.status = 0;
        this.message = "";
    }


    public WebApplicationException(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public WebApplicationException(Throwable cause, int status, String message) {
        super(cause);
        this.status = status;
        this.message = message;
    }

    public WebApplicationException(Throwable cause, int status) {
        this(cause, status, "");
    }

    public WebApplicationException(int status) {
        this(status, "");
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
