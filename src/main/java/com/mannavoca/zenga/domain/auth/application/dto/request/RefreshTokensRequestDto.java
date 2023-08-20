package com.mannavoca.zenga.domain.auth.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class RefreshTokensRequestDto {
    @NotBlank(message = "refreshToken은 필수입니다.")
    private String refreshToken;
}
