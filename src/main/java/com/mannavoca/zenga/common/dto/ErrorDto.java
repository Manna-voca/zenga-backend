package com.mannavoca.zenga.common.dto;

import com.google.firebase.ErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorDto {
    private int errorCode;
    private String message;
    private LocalDateTime timestamp;
    private String log;

    @Builder
    public ErrorDto(int errorCode, String message, String log) {
        this.errorCode = errorCode;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.log = log;
    }

    public static ErrorDto of(int errorCode, String message, String log) {
        return ErrorDto.builder()
                .errorCode(errorCode)
                .message(message)
                .log(log)
                .build();
    }
}
