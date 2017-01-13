package com.showyourtrace.exception;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationException extends ApplicationRuntimeException {
	private final List<ValidationError> errors;

    public ValidationException() {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), ApplicationCode.VALIDATION.getMessage());
        errors = new ArrayList<ValidationError>(1);
        //errors.add(new ValidationError("Ошибка валидации"));
    }

    public ValidationException(String message, Throwable cause) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), message);
        errors = new ArrayList<ValidationError>(1);
        errors.add(new ValidationError(cause.getMessage()));
    }

    public ValidationException(String message) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), message);
        errors = new ArrayList<ValidationError>(1);
        errors.add(new ValidationError(message));
    }

    public ValidationException(int httpStatus, String errorCode, String errorMessage, List<ValidationError> errors) {
        super(httpStatus, errorCode, errorMessage);
        this.errors = errors;
    }

    public ValidationException(String errorCode, String errorMessage, List<ValidationError> errors) {
        super(errorCode, errorMessage);
        this.errors = errors;
    }


    public ValidationException(String message, List<ValidationError> errors) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), message);
        this.errors = errors;
    }



    public ValidationException(List<ValidationError> errors) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), ApplicationCode.VALIDATION.getMessage());
        this.errors = errors;
    }

    public ValidationException(Errors errors) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), ApplicationCode.VALIDATION.getMessage());
        this.errors = new ArrayList<ValidationError>(errors.getFieldErrors().size());
        for(FieldError constraintViolation : errors.getFieldErrors()) {
            ValidationError error = new ValidationError(
                    constraintViolation.getField(),
                    constraintViolation.getRejectedValue() != null ? constraintViolation.getRejectedValue().toString() : null,
                    constraintViolation.getDefaultMessage()
            );
            this.errors.add(error);
        }
    }

    public ValidationException(Set<? extends ConstraintViolation<?>> violations) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.VALIDATION.getCode(), ApplicationCode.VALIDATION.getMessage());
        errors = new ArrayList<ValidationError>(violations.size());
        for(ConstraintViolation<?> constraintViolation : violations) {
            ValidationError error = new ValidationError(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null,
                    constraintViolation.getMessage()
            );
            errors.add(error);
        }
    }


    public List<ValidationError> getErrors() {
        return errors;
    }

    public void addError(ValidationError error) {
        this.errors.add(error);
    }

}
