package com.mannavoca.zenga.common.security.exception;

import com.mannavoca.zenga.common.exception.Error;

public class ExpiredTokenException extends JwtException{
    public ExpiredTokenException(Error error) {
        super(error);
    }
}
