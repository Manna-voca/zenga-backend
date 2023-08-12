package com.mannavoca.zenga.domain.user.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.user.application.dto.response.UserInfoResponseDto;
import com.mannavoca.zenga.domain.user.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getUserInfo() {
        return ResponseEntity.ok(ResponseDto.success(userService.getUserInfo(SecurityUtils.getUserId())));
    }
}
