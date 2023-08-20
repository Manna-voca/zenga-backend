package com.mannavoca.zenga.domain.auth.application.service.kakao;

import com.mannavoca.zenga.common.config.openfeign.OpenFeignEncodingConfig;
import com.mannavoca.zenga.domain.auth.application.dto.request.KakaoOAuthRequestDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.KakaoOAuthResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "KakaoOAuthClient",
        url = "https://kauth.kakao.com",
        configuration = OpenFeignEncodingConfig.class
)
public interface KakaoOAuthClient {
    @PostMapping(value = "/oauth/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    KakaoOAuthResponseDto getKakaoOAuthToken(@RequestBody KakaoOAuthRequestDto kakaoOauthRequestDto);
}
