package com.mannavoca.zenga.common.dto;

import com.mannavoca.zenga.common.ResponseCode.ResponseCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseDto<T> {
    private ResponseCode code;
    private String message;
    private LocalDateTime timestamp;
    private T data;

    @Builder
    public ResponseDto(ResponseCode code, String message, T data) {
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }


    public static <T> ResponseDto<T> of(ResponseCode code, T data) {
        return ResponseDto.<T>builder()
                .code(code)
                .message(code.getMessage())
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> success(T data) {
        return ResponseDto.<T>builder()
                .code(ResponseCode.OK)
                .message(ResponseCode.OK.getMessage())
                .data(data)
                .build();
    }

    public static <T> ResponseDto<T> success() {
        return ResponseDto.<T>builder()
                .code(ResponseCode.OK)
                .message(ResponseCode.OK.getMessage())
                .build();
    }
}
