package com.mannavoca.zenga.domain.member.presentation;

import com.mannavoca.zenga.common.dto.ResponseDto;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.domain.member.application.dto.request.CreatingMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.service.MemberCreateUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/members")
public class MemberController {
    private final MemberCreateUseCase memberCreateUseCase;

    @PostMapping
    public ResponseEntity<ResponseDto<MemberInfoResponseDto>> createMember(@RequestBody CreatingMemberRequestDto creatingMemberRequestDto) {
        return ResponseEntity.ok(ResponseDto.success(memberCreateUseCase.createMember(creatingMemberRequestDto)));
    }


}
