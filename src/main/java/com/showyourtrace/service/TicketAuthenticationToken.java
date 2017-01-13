package com.languagelearn.service;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class TicketAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    private final int keyHash;

//    public TicketAuthenticationToken(Object key) {
//        super(null);
//
//        if ((key == null) || ("".equals(key))) {
//            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
//        }
//
//        this.principal = key;
//        this.keyHash = key.hashCode();
//        setAuthenticated(false);
//    }

    public TicketAuthenticationToken(String key, Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);

        if ((key == null) || ("".equals(key)) || (principal == null) || "".equals(principal)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.keyHash = key.hashCode();
        this.principal = principal;
        setAuthenticated(true);
    }

    public Object getCredentials() {
        return "";
    }

    public int getKeyHash() {
        return this.keyHash;
    }

    public Object getPrincipal() {
        return this.principal;
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }

        if (obj instanceof TicketAuthenticationToken) {
            TicketAuthenticationToken test = (TicketAuthenticationToken) obj;

            if (this.getKeyHash() != test.getKeyHash()) {
                return false;
            }

            return true;
        }

        return false;
    }

}
