package com.languagelearn.exception;

public class ValidationError {
    private final String propertyName;
    private final String propertyValue;
    private final String message;


    public ValidationError(String message) {
        this(null, null, message);
    }

    public ValidationError(String propertyName, String propertyValue, String message) {
        this.propertyName = propertyName;
        this.propertyValue = propertyValue;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ValidationError{" +
                "propertyName='" + propertyName + '\'' +
                ", propertyValue='" + propertyValue + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getPropertyName() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public String getMessage() {
        return message;
    }
}
