package com.mannavoca.zenga.domain.auth.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.auth.application.dto.request.RefreshTokensRequestDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.TokenResponseDto;
import com.mannavoca.zenga.domain.auth.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/kakao")
    public ResponseEntity<ResponseDto<TokenResponseDto>> loginByKakaoAuthCode(@RequestParam @NotBlank(message = "인가 코드는 필수입니다.") final String code, @RequestParam(required = false) final String redirectUri) {
        return ResponseEntity.ok(ResponseDto.success(authService.generateTokens(code, redirectUri)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<TokenResponseDto>> refreshTokens(@Valid @RequestBody final RefreshTokensRequestDto refreshToken) {
        return ResponseEntity.ok(ResponseDto.success(authService.refreshTokens(refreshToken)));
    }

    @PutMapping("/change-redirect-uri")
    public ResponseEntity<ResponseDto<String>> changeRedirectUri(@RequestParam @NotBlank(message = "Redirect URI는 필수입니다.") final String redirectUri) {
        return ResponseEntity.ok(ResponseDto.success(authService.changeRedirectUri(redirectUri)));
    }
}
