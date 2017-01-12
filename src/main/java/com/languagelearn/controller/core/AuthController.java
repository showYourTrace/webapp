package com.languagelearn.controller.core;

import com.languagelearn.exception.ErrorHandler;
import com.languagelearn.exception.ErrorResponse;
import com.languagelearn.object.response.UserResponse;
import com.languagelearn.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping({"/api/auth", "/admin/api/auth"})
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ErrorHandler errorHandler;
    @Autowired
    private AuthenticationService authenticationService;


    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleExceptions(Exception ex, HttpServletResponse response) {
        return errorHandler.handleExceptions(ex, response);
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public UserResponse current() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationService.view(authentication);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void logout() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authenticationService.logout(authentication);
    }

}
