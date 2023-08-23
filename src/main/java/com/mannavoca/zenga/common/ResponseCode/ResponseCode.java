package com.mannavoca.zenga.common.ResponseCode;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseCode {

    // Common
    OK(HttpStatus.OK, "정상적으로 처리되었습니다.", "O200"),

    // party
    PARTY_CREATED(HttpStatus.OK, "모임 생성을 성공하였습니다.", "P001"),
    PARTY_UPDATED(HttpStatus.OK, "모임 정보 수정을 성공하였습니다.", "P002"),
    PARTY_DELETED(HttpStatus.OK, "모임 삭제를 성공하였습니다.", "P003"),
    PARTY_JOIN_CANCELED(HttpStatus.OK, "모임 가입을 취소하였습니다.", "P004"),
    PARTY_CARD_CREATED(HttpStatus.OK, "모임 카드 생성에 성공하였습니다.", "P005"),
    PARTY_CARD_DELETED(HttpStatus.OK, "모임 카드 삭제에 성공하였습니다.", "P006"),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final String code;

    ResponseCode(HttpStatus httpStatus, String message, String code) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.code = code;
    }
}
