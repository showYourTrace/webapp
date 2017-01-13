package com.showyourtrace.controller;

import com.showyourtrace.domain.UserDomain;
import com.showyourtrace.exception.ApplicationRuntimeException;
import com.showyourtrace.exception.ErrorHandler;
import com.showyourtrace.exception.ErrorResponse;
import com.showyourtrace.object.request.RegisterUserRequest;
import com.showyourtrace.object.request.UserCreateRequest;
import com.showyourtrace.object.request.UserUpdateRequest;
import com.showyourtrace.object.response.ConfirmResultResponse;
import com.showyourtrace.object.response.UserResponse;
import com.showyourtrace.service.mailer.Mailer;
import com.showyourtrace.util.ApplicationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@RequestMapping("/api")
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private UserDomain userDomain;

    @Autowired
    private ErrorHandler errorHandler;

    @Autowired
    private Mailer mailer;
    @Autowired
    private ApplicationProperties applicationProperties;

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public UserResponse registerUser(@RequestBody RegisterUserRequest request) {
        UserResponse result = userDomain.registerUser(request);
        mailer.send(result.getEmail(), "REGISTRATION", "User " + result.getFullName() + " was registered!");
        return result;
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    @ResponseBody
    public boolean subscribe(@RequestBody String email) {
        String uniqueId = UUID.randomUUID().toString();
        String confirmLink = applicationProperties.subscriptionConfirmUrl + uniqueId;
        String txt = "" + confirmLink;

        UserCreateRequest newUser = new UserCreateRequest();
        newUser.setEmail(email);
        newUser.setReceivePromo(false);
        newUser.setConfirmId(uniqueId);
        userDomain.registerSubscriber(newUser);

        try {
            mailer.send(email, "SUBSCRIBE" ,txt);
        }
        catch (MailException ex) {
            log.error(ex.getMessage(), ex);
            throw new ApplicationRuntimeException("Error occured. Please, try again later.");
        }

        return false;
    }

    @RequestMapping(value = "/resendConfirmation", method = RequestMethod.POST)
    @ResponseBody
    public boolean resendConfirmation(@RequestBody String email) {
        String uniqueId = UUID.randomUUID().toString();
        String confirmLink = applicationProperties.subscriptionConfirmUrl + uniqueId;
        String txt = applicationProperties.msgFollowTheLink + confirmLink;

        UserUpdateRequest confirmUpdate = new UserUpdateRequest();
        confirmUpdate.setEmail(email);
        confirmUpdate.setReceivePromo(false);
        confirmUpdate.setConfirmId(uniqueId);
        userDomain.updateConfirm(confirmUpdate);

        try {
            mailer.send(email, "SUBSCRIBE CONFIRMATION",  txt);
        }
        catch (MailException ex) {
            log.error(ex.getMessage(), ex);
            throw new ApplicationRuntimeException("Error occured. Please, try again later.");
        }

        return false;
    }

    @RequestMapping(value = "/confirm/{confirmId}", method = RequestMethod.GET)
    @ResponseBody
    public ConfirmResultResponse confirm(@PathVariable String confirmId) {
        return userDomain.confirm(confirmId);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestExceptions(Exception ex, HttpServletResponse response) {
        ex.printStackTrace();
        return errorHandler.handleExceptions(ex, response);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalServerError(Exception ex, HttpServletResponse response) {
        ex.printStackTrace();
        return errorHandler.handleExceptions(ex, response);
    }

}
