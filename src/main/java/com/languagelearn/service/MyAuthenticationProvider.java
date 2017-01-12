package com.languagelearn.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import javax.annotation.Resource;

@Service("myAuthenticationProvider")
public class MyAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private Authorize authorize;
    @Resource(name = "myUserCache")
    private UserCache userCache;


    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        if(!authorize.authorize(username, password)) {
            logger.debug("Authentication failed: password does not match stored value");
            throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser;

        try {
            loadedUser = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException notFound) {
            throw notFound;
        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }

        if (loadedUser == null) {
            throw new AuthenticationServiceException(
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(UsernamePasswordAuthenticationToken.class, authentication,
                messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only UsernamePasswordAuthenticationToken is supported"));

        // Determine username
        String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();

        boolean cacheWasUsed = true;
        UserDetails user = this.userCache.getUserFromCache(username);

        if (user == null) {
            cacheWasUsed = false;

            try {
                user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
            } catch (UsernameNotFoundException notFound) {
                logger.debug("User '" + username + "' not found");

                if (hideUserNotFoundExceptions) {
                    throw new BadCredentialsException(messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
                } else {
                    throw notFound;
                }
            }

            Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        }

        try {
            getPreAuthenticationChecks().check(user);
            additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
        } catch (AuthenticationException exception) {
            if (cacheWasUsed) {
                // There was a problem, so try again after checking
                // we're using latest data (i.e. not from the cache)
                cacheWasUsed = false;
                user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);
                getPreAuthenticationChecks().check(user);
                additionalAuthenticationChecks(user, (UsernamePasswordAuthenticationToken) authentication);
            } else {
                throw exception;
            }
        }

        getPostAuthenticationChecks().check(user);
        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }
        Object principalToReturn = user;
        return createSuccessAuthentication(principalToReturn, authentication, user);
    }

}