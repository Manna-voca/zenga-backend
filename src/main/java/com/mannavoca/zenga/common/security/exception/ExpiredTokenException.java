package com.mannavoca.zenga.common.security.exception;


public class ExpiredTokenException extends JwtException{
    public ExpiredTokenException(Error error) {
        super(error);
    }
}
