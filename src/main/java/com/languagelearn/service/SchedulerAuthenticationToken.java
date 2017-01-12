//package com.couponworld.service;
//
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//public class SchedulerAuthenticationToken implements Authentication {
//    private String userName;
//
//    public SchedulerAuthenticationToken(String userName) {
//        this.userName = userName;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public Object getCredentials() {
//        return null;
//    }
//
//    @Override
//    public Object getDetails() {
//        return null;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return null;
//    }
//
//    @Override
//    public boolean isAuthenticated() {
//        return false;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//
//    }
//
//    @Override
//    public String getName() {
//        return userName;
//    }
//}
