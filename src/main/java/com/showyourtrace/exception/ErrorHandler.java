package com.showyourtrace.exception;

import javax.servlet.http.HttpServletResponse;

public interface ErrorHandler {
    ErrorResponse handleExceptions(Exception ex, HttpServletResponse response);
}
