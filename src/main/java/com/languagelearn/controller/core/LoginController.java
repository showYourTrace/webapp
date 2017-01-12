package com.languagelearn.controller.core;

import com.languagelearn.exception.ErrorHandler;
import com.languagelearn.exception.ErrorResponse;
import com.languagelearn.object.request.AuthorizationRequest;
import com.languagelearn.object.response.UserResponse;
import com.languagelearn.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/web/login")
public class LoginController {

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


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserResponse login(@RequestBody AuthorizationRequest model) throws Exception {
        Authentication authentication = authenticationService.login(model);
        return authenticationService.view(authentication);
    }

}