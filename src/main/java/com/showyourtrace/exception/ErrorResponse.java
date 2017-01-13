package com.languagelearn.exception;

public class ErrorResponse {
    private String errorCode;
    private String developerMessage;
    private String applicationMessage;
    private final Long timeStamp;

    public ErrorResponse() {
        timeStamp = new Long(System.currentTimeMillis());
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public String getApplicationMessage() {
        return applicationMessage;
    }

    public void setApplicationMessage(String applicationMessage) {
        this.applicationMessage = applicationMessage;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }
}
