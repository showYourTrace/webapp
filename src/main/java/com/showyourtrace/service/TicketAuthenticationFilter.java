//package com.couponworld.service;
//
//import java.io.IOException;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.AccountStatusException;
//import org.springframework.security.authentication.AuthenticationDetailsSource;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationServiceException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.security.web.authentication.rememberme.InvalidCookieException;
//import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationException;
//import org.springframework.web.filter.GenericFilterBean;
//
//public class TicketAuthenticationFilter extends GenericFilterBean {
//    private Authorize authorize;
//    private UserDetailsService userDetailsService;
//    private AuthenticationManager authenticationManager;
//    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
//    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
//    private String key = "pe4enka";
//
//    protected UserDetails processAutoLogin(String token, HttpServletRequest request,
//                                                 HttpServletResponse response) {
//        String username = authorize.ticket(token);
//        if(username != null && username.length() > 0) {
//            UserDetails loadedUser;
//            try {
//                loadedUser = userDetailsService.loadUserByUsername(username);
//            } catch (UsernameNotFoundException notFound) {
//                throw notFound;
//            } catch (Exception repositoryProblem) {
//                throw new AuthenticationServiceException(repositoryProblem.getMessage(), repositoryProblem);
//            }
//            //if(loadedUser == null) {
//            //    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(2);
//            //    authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//            //    loadedUser = new User(username, username, true, true, true, true, authorities);
//            //}
//            return loadedUser;
//        }
//        return null;
//    }
//
//    protected Authentication createSuccessfulAuthentication(HttpServletRequest request, UserDetails user) {
//        TicketAuthenticationToken result = new TicketAuthenticationToken(extractToken(request), user, authoritiesMapper.mapAuthorities(user.getAuthorities()));
//        result.setDetails(authenticationDetailsSource.buildDetails(request));
//        return result;
//    }
//
//    protected String extractToken(HttpServletRequest request) {
//        return request.getParameter(key);
//    }
//
//    protected String decodeToken(String tokenValue) throws InvalidCookieException {
//        return tokenValue;
//    }
//
//    public final Authentication authentication(HttpServletRequest request, HttpServletResponse response) {
//        String token = extractToken(request);
//
//        if (token == null || token.length() == 0) {
//            return null;
//        }
//        logger.debug("Ticket token detected");
//        UserDetails user = null;
//        try {
//            token = decodeToken(token);
//            user = processAutoLogin(token, request, response);
//            if(user != null) {
//                logger.debug("Ticket token accepted");
//                return createSuccessfulAuthentication(request, user);
//            }
//        } catch (UsernameNotFoundException noUser) {
//            logger.error("Ticket token login was valid but corresponding user not found.", noUser);
//        } catch (InvalidCookieException invalidCookie) {
//            logger.error("Invalid ticket token: " + invalidCookie.getMessage());
//        } catch (AccountStatusException statusInvalid) {
//            logger.error("Invalid UserDetails: " + statusInvalid.getMessage());
//        } catch (RememberMeAuthenticationException e) {
//            logger.error(e.getMessage());
//        }
//        return null;
//    }
//
//
//    @Override
//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) res;
//
//        if (SecurityContextHolder.getContext().getAuthentication() == null) {
//            Authentication ticketAuth = authentication(request, response);
//
//            if (ticketAuth != null) {
//                // Attempt authenticaton via AuthenticationManager
//                try {
//                    ticketAuth = authenticationManager.authenticate(ticketAuth);
//
//                    // Store to SecurityContextHolder
//                    SecurityContextHolder.getContext().setAuthentication(ticketAuth);
//
//                    onSuccessfulAuthentication(request, response, ticketAuth);
//
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("SecurityContextHolder populated with ticket token: '"
//                                + SecurityContextHolder.getContext().getAuthentication() + "'");
//                    }
//                    // Fire event
////                    if (this.eventPublisher != null) {
////                        eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
////                                SecurityContextHolder.getContext().getAuthentication(), this.getClass()));
////                    }
////
////                    if (successHandler != null) {
////                        successHandler.onAuthenticationSuccess(request, response, rememberMeAuth);
////
////                        return;
////                    }
//
//                } catch (AuthenticationException authenticationException) {
//                    if (logger.isDebugEnabled()) {
//                        logger.debug("SecurityContextHolder not populated with ticket token, as "
//                                + "AuthenticationManager rejected Authentication returned by RememberMeServices: '"
//                                + ticketAuth + "'; invalidating ticket token", authenticationException);
//                    }
//                    onUnsuccessfulAuthentication(request, response, authenticationException);
//                }
//            }
//            chain.doFilter(request, response);
//        } else {
//            if (logger.isTraceEnabled()) {
//                logger.trace("SecurityContextHolder not populated with ticket token, as it already contained: '"
//                        + SecurityContextHolder.getContext().getAuthentication() + "'");
//            }
//
//            chain.doFilter(request, response);
//        }
//    }
//
//    protected void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                              Authentication authResult) {
//    }
//
//    /**
//     * Called if the {@code AuthenticationManager} rejects the authentication object returned from the
//     * {@code RememberMeServices} {@code autoLogin} method. This method will not be called when no remember-me
//     * token is present in the request and {@code autoLogin} reurns null.
//     */
//    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                                AuthenticationException failed) {
//    }
//
//
//    public Authorize getAuthorize() {
//        return authorize;
//    }
//
//    public void setAuthorize(Authorize authorize) {
//        this.authorize = authorize;
//    }
//
//    public UserDetailsService getUserDetailsService() {
//        return userDetailsService;
//    }
//
//    public void setUserDetailsService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    public AuthenticationManager getAuthenticationManager() {
//        return authenticationManager;
//    }
//
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }
//
//    public AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
//        return authenticationDetailsSource;
//    }
//
//    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
//        this.authenticationDetailsSource = authenticationDetailsSource;
//    }
//
//    public GrantedAuthoritiesMapper getAuthoritiesMapper() {
//        return authoritiesMapper;
//    }
//
//    public void setAuthoritiesMapper(GrantedAuthoritiesMapper authoritiesMapper) {
//        this.authoritiesMapper = authoritiesMapper;
//    }
//
//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }
//}
