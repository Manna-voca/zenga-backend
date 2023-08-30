package com.mannavoca.zenga.domain.user.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.domain.user.application.dto.request.UpdatingUserInfoRequestDto;
import com.mannavoca.zenga.domain.user.application.dto.response.UserInfoResponseDto;
import com.mannavoca.zenga.domain.user.application.service.UserReadUseCase;
import com.mannavoca.zenga.domain.user.application.service.UserUpdateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserReadUseCase userReadUseCase;
    private final UserUpdateUseCase userUpdateUseCase;

    @GetMapping("/info")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> getUserInfo() {
        return ResponseEntity.ok(ResponseDto.success(userReadUseCase.getUserInfo()));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto<UserInfoResponseDto>> updateUser(@Valid @RequestBody final UpdatingUserInfoRequestDto updatingUserInfoRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(userUpdateUseCase.updateUser(updatingUserInfoRequestDto)));
    }
}
