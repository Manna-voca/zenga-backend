package com.mannavoca.zenga.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum Error {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러입니다.", 500),

    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "데이터를 찾을 수 없습니다.", 800),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "s3 upload 오류입니다.", 801),
    FILE_EXTENTION_ERROR(HttpStatus.BAD_REQUEST,"파일 확장자 오류입니다.", 802),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"파일 삭제 오류입니다.", 803),

    // OAuth
    OAUTH_FAILED(HttpStatus.BAD_REQUEST,"잘못된 소셜 로그인 요청입니다. KAKAO, GOOGLE, APPLE 연동만이 가능합니다.", 890),
    GOOGLE_OAUTH_FAILED(HttpStatus.BAD_REQUEST,"Google에서 토큰을 검색하지 못했습니다.", 900),
    GOOGLE_OAUTH_FAILED2(HttpStatus.BAD_REQUEST,"Google에서 사용자 정보를 검색하지 못했습니다.", 901),
    KAKAO_OAUTH_FAILED(HttpStatus.BAD_REQUEST,"KAKAO 토큰이 만료되었습니다.", 910),
    KAKAO_OAUTH_FAILED2(HttpStatus.BAD_REQUEST,"KAKAO 토큰이 올바르지 않습니다.", 911),
    APPLE_OIDC_FAILED(HttpStatus.BAD_REQUEST,"Apple OAuth Identity Token 형식이 올바르지 않습니다.", 920),
    APPLE_OIDC_FAILED2(HttpStatus.BAD_REQUEST,"Apple OAuth 로그인 중 Identity Token 유효기간이 만료됐습니다.", 921),
    APPLE_OIDC_FAILED3(HttpStatus.BAD_REQUEST,"Apple OAuth Identity Token 값이 올바르지 않습니다.", 922),
    APPLE_OIDC_FAILED4(HttpStatus.BAD_REQUEST,"Apple JWT 값의 alg, kid 정보가 올바르지 않습니다.", 923),
    APPLE_OIDC_FAILED5(HttpStatus.BAD_REQUEST,"Apple OAuth 통신 암호화 과정 중 문제가 발생했습니다.", 924),
    APPLE_OIDC_FAILED6(HttpStatus.BAD_REQUEST,"Apple OAuth 로그인 중 public key 생성에 문제가 발생했습니다.", 925),
    APPLE_OIDC_FAILED7(HttpStatus.BAD_REQUEST,"Apple OAuth Claims 값이 올바르지 않습니다.", 926),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST,"이미 가입된 일반 계정입니다.", 930),
    NCP_SMS_FAILED(HttpStatus.INTERNAL_SERVER_ERROR,"SMS 전송 에러입니다. 다시 시도해주세요", 950),


    // 사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND,"사용자를 찾을 수 없습니다.", 1000),
    USER_NOT_CORRECT_SMS_NUM(HttpStatus.BAD_REQUEST,"잘못된 인증 번호입니다.", 1001),
    USER_NICKNAME_DUPLICATION(HttpStatus.BAD_REQUEST,"중복된 닉네임입니다.", 1002),

    // Praise
    CANNOT_SHUFFLE(HttpStatus.BAD_REQUEST, "더 이상 셔플할 수 없습니다.", 1100),
    ALREADY_PRAISED(HttpStatus.BAD_REQUEST, "이미 칭찬하기를 완료했습니다.", 1101),
    NOT_MEMBER_PRAISE_OWNER(HttpStatus.BAD_REQUEST, "칭찬을 열 권한이 없습니다.", 1102),

    // Point
    NOT_ENOUGH_POINT(HttpStatus.BAD_REQUEST, "포인트가 부족합니다.", 1102),

    // JWT
    INVALID_TOKEN(HttpStatus.BAD_REQUEST,"잘못된 토큰 요청", 7000),
    EXPIRED_TOKEN(HttpStatus.BAD_REQUEST,"토큰 만료되었습니다. 토큰 재발행 혹은 로그인을 다시 해주세요", 7001),
    ;

    private final HttpStatus httpStatus;
    private final String message;
    private final int errorCode;

    Error(HttpStatus httpStatus, String message, int errorCode) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.errorCode = errorCode;
    }
}
