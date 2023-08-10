package com.mannavoca.zenga.domain.auth.application.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class KakaoOAuthRequestDto {
    private String grant_type = "authorization_code";  // 기본값을 'authorization_code'로 설정
    private String client_id;
    private String redirect_uri;
    private String code;
    private String client_secret;  // 이 필드는 client_secret이 ON 상태일 때만 필요

    @Builder
    public KakaoOAuthRequestDto(String client_id, String redirect_uri, String code, String client_secret) {
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.client_secret = client_secret;
    }

}







