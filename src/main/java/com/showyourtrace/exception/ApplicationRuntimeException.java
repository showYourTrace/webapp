package com.showyourtrace.exception;

import javax.servlet.http.HttpServletResponse;

public class ApplicationRuntimeException extends BaseWebApplicationException {

    public ApplicationRuntimeException(String errorCode, String errorMessage) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorCode, errorMessage);
    }

    public ApplicationRuntimeException(int httpStatus, String errorCode, String errorMessage) {
        super(httpStatus, errorCode, errorMessage);
    }

    public ApplicationRuntimeException(String applicationMessage) {
        super(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ApplicationCode.APPLICATION_RUNTIME.getCode(), applicationMessage);
    }


}
