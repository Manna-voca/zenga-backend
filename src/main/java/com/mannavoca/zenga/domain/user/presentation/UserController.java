package com.mannavoca.zenga.domain.user.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.user.application.dto.request.UserOnboardingRequestDto;
import com.mannavoca.zenga.domain.user.application.dto.response.UserInfoResponseDto;
import com.mannavoca.zenga.domain.user.application.service.UserReadUseCase;
import com.mannavoca.zenga.domain.user.application.service.UserUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserReadUseCase userReadUseCase;
    private final UserUpdateUseCase userUpdateUseCase;


    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getUserInfo() {
        return ResponseEntity.ok(ResponseDto.success(userReadUseCase.getUserInfo(SecurityUtils.getUserId())));
    }

    @PutMapping("/onboard")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> onboardUser(@RequestBody UserOnboardingRequestDto userOnboardingRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(userUpdateUseCase.onboardUser(SecurityUtils.getUserId(), userOnboardingRequestDto)));
    }
}
