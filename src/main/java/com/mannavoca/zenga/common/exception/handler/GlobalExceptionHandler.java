package com.mannavoca.zenga.common.exception.handler;

import com.mannavoca.zenga.common.dto.ErrorDto;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ValidationException;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {BusinessException.class})
    public ResponseEntity<ErrorDto> handlerCustomException(BusinessException e) {
        log.error("Status: {}, Message: {}", e.getError().getErrorCode(), e.getMessage());

        String log = e.getError().getErrorCode() == Error.INTERNAL_SERVER_ERROR.getErrorCode() ? e.getMessage() : null;

        return ResponseEntity
                .status(e.getError().getHttpStatus())
                .body(ErrorDto.of(e.getError().getErrorCode(), e.getError().getMessage(), log));
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<ErrorDto> handlerValidationException(ValidationException e) {
        log.error("Status: {}, Message: {}", Error.INVALID_INPUT_VALUE.getErrorCode(), e.getMessage());

        return ResponseEntity
                .status(Error.INVALID_INPUT_VALUE.getHttpStatus())
                .body(ErrorDto.of(Error.INVALID_INPUT_VALUE.getErrorCode(), Error.INVALID_INPUT_VALUE.getMessage(), e.getMessage()));
    }
}
