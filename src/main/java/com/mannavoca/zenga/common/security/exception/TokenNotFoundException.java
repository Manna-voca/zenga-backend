package com.mannavoca.zenga.common.security.exception;
import com.mannavoca.zenga.common.exception.Error;

public class TokenNotFoundException extends JwtException{
    public TokenNotFoundException(Error error) {
        super(error);
    }
}
