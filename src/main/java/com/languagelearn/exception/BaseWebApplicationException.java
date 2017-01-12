package com.languagelearn.exception;

public abstract class BaseWebApplicationException extends WebApplicationException {
    private final String errorCode;

    public BaseWebApplicationException(int httpStatus, String errorCode, String errorMessage) {
        super(httpStatus, errorMessage);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }
}
