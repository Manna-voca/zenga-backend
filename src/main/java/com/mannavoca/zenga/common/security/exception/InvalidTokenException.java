package com.mannavoca.zenga.common.security.exception;


public class InvalidTokenException extends JwtException{
    public InvalidTokenException(Error error) {
        super(error);
    }
}
