package com.mannavoca.zenga.domain.user.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.user.application.dto.request.OnboardingUserRequestDto;
import com.mannavoca.zenga.domain.user.application.dto.response.UserInfoResponseDto;
import com.mannavoca.zenga.domain.user.application.mapper.UserMapper;
import com.mannavoca.zenga.domain.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class UserUpdateUseCase {
    private final UserService userService;

    public UserInfoResponseDto onboardUser(Long userId, OnboardingUserRequestDto onboardingUserRequestDto) {
        return UserMapper.mapUserToUserInfoResponseDto(userService.onboardUser(userId, onboardingUserRequestDto));
    }
}
