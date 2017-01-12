package com.languagelearn.exception;

public enum ApplicationCode {
    SYSTEM("500", "Internal System error"),
    APPLICATION_RUNTIME("500", "Internal System error"),
    VALIDATION("500", "The data passed in the request was invalid. Please check and resubmit"),
    REPOSITORY_NOT_FOUND("500", "Repository not found"),
    ACCESS_DENIED("500", "Access Denied Error"),
    AUTHENTICATION("500", "Authentication Error"),
    REPOSITORY_VALIDATION("500", "Repository Validation Error");

    private final String code;
    private final String message;

    private ApplicationCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

}
