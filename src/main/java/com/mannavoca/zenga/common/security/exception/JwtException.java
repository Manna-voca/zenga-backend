package com.mannavoca.zenga.common.security.exception;


import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;

public class JwtException extends BusinessException {
    public JwtException(Error error) {
        super(ResponseDto.of(error.getErrorCode(), error.getMessage(), null));
    }
}
