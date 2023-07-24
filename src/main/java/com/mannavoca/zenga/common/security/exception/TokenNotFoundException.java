package com.mannavoca.zenga.common.security.exception;

public class TokenNotFoundException extends JwtException{
    public TokenNotFoundException(Error error) {
        super(error);
    }
}
