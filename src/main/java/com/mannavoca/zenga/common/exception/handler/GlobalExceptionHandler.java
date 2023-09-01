package com.mannavoca.zenga.common.exception.handler;

import com.mannavoca.zenga.common.dto.ErrorDto;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
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

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handlerValidationException(ConstraintViolationException e) {
        log.error("Status: {}, Message: {}", Error.INVALID_API_INPUT_PARAMETER.getErrorCode(), e.getMessage());

        return ResponseEntity
                .status(Error.INVALID_API_INPUT_PARAMETER.getHttpStatus())
                .body(ErrorDto.of(Error.INVALID_API_INPUT_PARAMETER.getErrorCode(), Error.INVALID_API_INPUT_PARAMETER.getMessage(), e.getMessage()));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handlerValidationException(MethodArgumentNotValidException e) {
        log.error("Status: {}, Message: {}", Error.INVALID_API_INPUT_BODY.getErrorCode(), e.getBindingResult());

        return ResponseEntity
                .status(Error.INVALID_API_INPUT_BODY.getHttpStatus())
                .body(ErrorDto.of(Error.INVALID_API_INPUT_BODY.getErrorCode(), Error.INVALID_API_INPUT_BODY.getMessage(), e.getAllErrors().get(0).getDefaultMessage()));
    }
}
