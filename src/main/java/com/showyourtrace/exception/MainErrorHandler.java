package com.showyourtrace.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

@Component
public class MainErrorHandler implements ErrorHandler {

    private static Logger log = LoggerFactory.getLogger(MainErrorHandler.class);

    @Value(value = "#[error.developer.message]")
    private String developerMessage;

	public ErrorResponse handleExceptions(Exception ex, HttpServletResponse response) {
        if(ex instanceof org.springframework.security.access.AccessDeniedException) {
            return error((org.springframework.security.access.AccessDeniedException)ex, response);
        } else if (ex instanceof org.springframework.security.core.AuthenticationException) {
            return error((org.springframework.security.core.AuthenticationException)ex, response);
        } else if(ex instanceof WebApplicationException) {
            return error((WebApplicationException)ex, response);
        }  else {
            return error(ex, response);
        }
    }

    public String printStackTrace(Exception ex) {
        CharArrayWriter buffer = new CharArrayWriter();
        PrintWriter writer = new PrintWriter(buffer);
        ex.printStackTrace(writer);
        String stackTrace = buffer.toString();
        writer.close();
        return stackTrace;
    }

    public ErrorResponse error(Exception ex, HttpServletResponse response) {
        log.error("", ex);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ErrorResponse error = new ErrorResponse();
        error.setApplicationMessage(ex.getMessage());
        error.setDeveloperMessage(printStackTrace(ex));
        error.setErrorCode(ApplicationCode.SYSTEM.getCode());
        return error;
    }

    public ErrorResponse error(RuntimeException ex, HttpServletResponse response) {
        log.error("", ex);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ErrorResponse error = new ErrorResponse();
        error.setApplicationMessage(ex.getMessage());
        if(Boolean.parseBoolean(developerMessage)) {
            error.setDeveloperMessage(printStackTrace(ex));
        }
        error.setErrorCode(ApplicationCode.APPLICATION_RUNTIME.getCode());
        return error;
    }

    public ErrorResponse error(org.springframework.security.access.AccessDeniedException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(ApplicationCode.ACCESS_DENIED.getCode());
        error.setApplicationMessage(ApplicationCode.ACCESS_DENIED.getMessage());
        if(Boolean.parseBoolean(developerMessage)) {
            error.setDeveloperMessage(printStackTrace(ex));
        }
        return error;
    }

    public ErrorResponse error(org.springframework.security.core.AuthenticationException ex, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse error = new ErrorResponse();
        error.setErrorCode(ApplicationCode.AUTHENTICATION.getCode());
        error.setApplicationMessage(ApplicationCode.AUTHENTICATION.getMessage());
        if(Boolean.parseBoolean(developerMessage)) {
            error.setDeveloperMessage(printStackTrace(ex));
        }
        return error;
    }

    public ErrorResponse error(WebApplicationException ex, HttpServletResponse response) {
        log.error("", ex);
        response.setStatus(ex.getStatus());
        ErrorResponse error = new ErrorResponse();
        error.setApplicationMessage(ex.getMessage());
        error.setErrorCode(String.valueOf(ex.getStatus()));
        if(Boolean.parseBoolean(developerMessage)) {
            error.setDeveloperMessage(printStackTrace(ex));
        }
        return error;
    }

}
