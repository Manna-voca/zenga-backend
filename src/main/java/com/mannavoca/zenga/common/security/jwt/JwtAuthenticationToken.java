package com.mannavoca.zenga.common.security.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Long id;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities, Long id) {
        super(authorities);
        this.id = id;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return id;
    }
}
