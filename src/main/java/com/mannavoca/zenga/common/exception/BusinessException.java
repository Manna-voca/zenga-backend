package com.mannavoca.zenga.common.exception;

import com.mannavoca.zenga.common.dto.ErrorDto;
import com.mannavoca.zenga.common.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final Error error;
    public BusinessException(Error error) {
        this.error = error;
    }

    public static BusinessException of(Error error) {
        return new BusinessException(error);
    }
}
