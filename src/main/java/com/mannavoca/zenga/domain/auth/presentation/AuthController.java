package com.mannavoca.zenga.domain.auth.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.auth.application.dto.request.RefreshTokensRequestDto;
import com.mannavoca.zenga.domain.auth.application.dto.response.TokenResponseDto;
import com.mannavoca.zenga.domain.auth.domain.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @GetMapping("/login/kakao")
    public ResponseEntity<ResponseDto<TokenResponseDto>> loginByKakaoAuthCode(@NotNull @RequestParam String code) {
        return ResponseEntity.ok(ResponseDto.success(authService.generateTokens(code)));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseDto<TokenResponseDto>> refreshTokens(@RequestBody RefreshTokensRequestDto refreshToken) {
        return ResponseEntity.ok(ResponseDto.success(authService.refreshTokens(refreshToken)));
    }
}
