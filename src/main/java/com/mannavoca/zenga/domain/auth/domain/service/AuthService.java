package com.mannavoca.zenga.domain.auth.domain.service;

import com.mannavoca.zenga.common.security.jwt.JwtProvider;
import com.mannavoca.zenga.common.security.oauth.OAuthProperties;
import com.mannavoca.zenga.domain.auth.application.dto.request.KakaoOAuthRequestDto;
import com.mannavoca.zenga.domain.auth.application.dto.request.RefreshTokensRequestDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.KakaoOAuthResponseDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.TokenResponseDto;
import com.mannavoca.zenga.domain.auth.application.service.kakao.KakaoOAuthClient;
import com.mannavoca.zenga.domain.auth.application.service.kakao.KakaoOIDCHelper;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.service.UserFindService;
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
    private final UserFindService userFindService;

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
        String socialId = kakaoOIDCHelper.getPayloadFromIdToken(idToken).getSub();
        User user = userFindService.findOrCreateBySocialId(socialId);
        //id_token 디코딩 후 Sub값의 uuid로 accessToken 생성

        return TokenResponseDto.builder()
                .accessToken(jwtProvider.generateAccessToken(user.getId()))
                .refreshToken(jwtProvider.generateRefreshToken(user.getId()))
                .build();
    }

    public TokenResponseDto refreshTokens(RefreshTokensRequestDto refreshTokenDto) {
        String refreshToken = refreshTokenDto.getRefreshToken();
        jwtProvider.validateRefreshToken(refreshToken); // 저장된 리프레시와 받은 리프레시가 일치하는 지 검증

        Long userId = jwtProvider.extractId(refreshToken); // 리프레시 토큰에 담긴 userId가 실제로 존재하는 지 검증
        userFindService.validateUserId(userId);

        return TokenResponseDto.builder()
                .accessToken(jwtProvider.generateAccessToken(userId))
                .refreshToken(jwtProvider.generateRefreshToken(userId))
                .build();
    }
}
