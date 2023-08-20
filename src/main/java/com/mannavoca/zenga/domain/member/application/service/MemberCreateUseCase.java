package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class MemberCreateUseCase {
    private final MemberService memberService;

//    public MemberInfoResponseDto createMember(CreatingMemberRequestDto creatingMemberRequestDto) {
//        return MemberMapper.mapMemberToMemberInfoResponseDto(memberService.createMember(memberInfoRequestDto));
//    }
}
