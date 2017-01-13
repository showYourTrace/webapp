package com.showyourtrace.service;

import com.showyourtrace.object.request.AuthorizationRequest;
import com.showyourtrace.object.response.UserResponse;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {

    Authentication auth(String username);

    void logout(Authentication authentication);

    Authentication login(AuthorizationRequest request);

    UserResponse view(Authentication authentication);

}
