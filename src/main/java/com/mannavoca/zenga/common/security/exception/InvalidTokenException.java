package com.mannavoca.zenga.common.security.exception;

import com.mannavoca.zenga.common.exception.Error;


public class InvalidTokenException extends JwtException {
    public InvalidTokenException(Error error) {
        super(error);
    }
}
