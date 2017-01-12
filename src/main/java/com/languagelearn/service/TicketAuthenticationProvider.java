package com.languagelearn.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service("ticketAuthenticationProvider")
public class TicketAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    private UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    @Autowired
    private Authorize authorize;
    @Autowired
    private UserDetailsService userDetailsService;


    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  TicketAuthenticationToken authentication)
            throws AuthenticationException {

    }

    protected UserDetails retrieveUser(String username, TicketAuthenticationToken authentication)
            throws AuthenticationException {
        UserDetails loadedUser = null;

        try {
            loadedUser = userDetailsService.loadUserByUsername(username);
        } catch (UsernameNotFoundException notFound) {

        } catch (Exception repositoryProblem) {
            throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
        }
        return loadedUser;
    }

    protected String retrieveUserName(TicketAuthenticationToken authentication) {
        String hash = authentication.getPrincipal().toString();
        return authorize.ticket(hash);
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //return authentication;
        Assert.isInstanceOf(TicketAuthenticationToken.class, authentication,
                messages.getMessage("AbstractUserDetailsAuthenticationProvider.onlySupports",
                        "Only UsernamePasswordAuthenticationToken is supported"));

        // Determine username
        String username = retrieveUserName((TicketAuthenticationToken)authentication);
        if(username == null) {
            throw new BadCredentialsException(messages.getMessage(
                    "AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        UserDetails user = retrieveUser(username, (TicketAuthenticationToken) authentication);
        if(user == null) {
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            user = new User(username, authentication.getCredentials().toString(), true, true, true, true, authorities);
        }
        try {
            preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (TicketAuthenticationToken) authentication);
        } catch (AuthenticationException exception) {
            throw exception;
        }
        postAuthenticationChecks.check(user);
        return createSuccessAuthentication(authentication.getPrincipal().toString(), authentication, user);
    }

    protected Authentication createSuccessAuthentication(String principal, Authentication authentication, UserDetails user) {
        TicketAuthenticationToken result = new TicketAuthenticationToken(principal, user, authoritiesMapper.mapAuthorities(user.getAuthorities()));
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (TicketAuthenticationToken.class.isAssignableFrom(authentication));
    }



    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isAccountNonLocked()) {
                logger.debug("SecurityUser account is locked");

                throw new LockedException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.locked",
                        "SecurityUser account is locked"));
            }

            if (!user.isEnabled()) {
                logger.debug("SecurityUser account is disabled");

                throw new DisabledException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.disabled",
                        "SecurityUser is disabled"));
            }

            if (!user.isAccountNonExpired()) {
                logger.debug("SecurityUser account is expired");

                throw new AccountExpiredException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.expired",
                        "SecurityUser account has expired"));
            }
        }
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {
        public void check(UserDetails user) {
            if (!user.isCredentialsNonExpired()) {
                logger.debug("SecurityUser account credentials have expired");

                throw new CredentialsExpiredException(messages.getMessage(
                        "AbstractUserDetailsAuthenticationProvider.credentialsExpired",
                        "SecurityUser credentials have expired"));
            }
        }
    }

}
