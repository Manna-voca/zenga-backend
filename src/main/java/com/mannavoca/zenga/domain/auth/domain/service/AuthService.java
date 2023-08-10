package com.mannavoca.zenga.domain.auth.domain.service;

import com.mannavoca.zenga.common.security.jwt.JwtProvider;
import com.mannavoca.zenga.common.security.oauth.OAuthProperties;
import com.mannavoca.zenga.domain.auth.application.dto.request.KakaoOAuthRequestDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.KakaoOAuthResponseDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.TokenResponseDto;
import com.mannavoca.zenga.domain.auth.application.service.kakao.KakaoOAuthClient;
import com.mannavoca.zenga.domain.auth.application.service.kakao.KakaoOIDCHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final KakaoOAuthClient kakaoOAuthClient;
    private final KakaoOIDCHelper kakaoOIDCHelper;
    private final JwtProvider jwtProvider;
    private final OAuthProperties oAuthProperties;

    private String getIdToken(String code) {
        KakaoOAuthRequestDto kakaoOAuthRequestDto = KakaoOAuthRequestDto.builder()
                .client_id(oAuthProperties.getClientId())
                .client_secret(oAuthProperties.getClientSecret())
                .redirect_uri(oAuthProperties.getRedirectUri())
                .code(code)
                .build();

        KakaoOAuthResponseDto responseDto = kakaoOAuthClient.getKakaoOAuthToken(kakaoOAuthRequestDto);
        return responseDto.getId_token();
    }

    public TokenResponseDto generateTokens(String code) {
        String idToken = getIdToken(code);
        String uuid = kakaoOIDCHelper.getPayloadFromIdToken(idToken).getSub();
        //id_token 디코딩 후 Sub값의 uuid로 accessToken 생성

        return TokenResponseDto.builder()
                .accessToken(jwtProvider.generateAccessToken(uuid))
                .refreshToken(jwtProvider.generateRefreshToken(uuid))
                .build(); // TODO: 리프레시 토큰 레디스에 저장하는 로직 구현 필요, 사용자 검증 로직 구현 필요
    }

}
