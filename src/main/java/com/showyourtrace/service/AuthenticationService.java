package com.languagelearn.service;

import com.languagelearn.object.request.AuthorizationRequest;
import com.languagelearn.object.response.UserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    Authentication auth(String username);

    void logout(Authentication authentication);

    Authentication login(AuthorizationRequest request);

    UserResponse view(Authentication authentication);

}
