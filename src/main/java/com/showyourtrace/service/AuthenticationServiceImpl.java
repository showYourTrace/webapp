package com.languagelearn.service;

import com.languagelearn.exception.ValidationException;
import com.languagelearn.model.User;
import com.languagelearn.object.encode.UserEncode;
import com.languagelearn.object.request.AuthorizationRequest;
import com.languagelearn.object.response.UserResponse;
import com.languagelearn.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    
	@Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserEncode userEncode;

    @Autowired
    private Validator validator;
    

    
    private void validate(Object request) {
        Set<? extends ConstraintViolation<?>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() > 0) {
            throw new ValidationException(constraintViolations);
        }
    }

    protected Authentication authentication(String username, String password) {
        Authentication authResult = null;
        try {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
            authResult = authenticationManager.authenticate(authentication);
            SecurityContextHolder.getContext().setAuthentication(authResult);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw e;
        }
        return authResult;
    }

    @Transactional
    @Override
    public Authentication auth(String username) {
        User user = userRepository.findByUserName(username);
        if(user != null) {
            return authentication(user.getLogin(), user.getPwd());
        }
        return null;
    }

    @Override
    public void logout(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
    }

    @Override
    public Authentication login(AuthorizationRequest request) {
        validate(request);
        Authentication authentication = authentication(request.getLogin(), request.getPwd());
        return authentication;
    }

    @Transactional
    @Override
    public UserResponse view(Authentication authentication) {
        if(authentication != null && authentication.isAuthenticated()) {
            User user = userRepository.findByUserName(authentication.getName());
            UserResponse result = userEncode.encode(user);
            return result;
        }
        return null;
    }
}
