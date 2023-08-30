package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberModalPermitResponseDto;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class MemberModalCheckUseCase {
    private final UserUtils userUtils;

    public MemberModalPermitResponseDto getMemberModalPermit(Long channelId) {
        Member member = userUtils.getMember(channelId);
        return MemberModalPermitResponseDto.builder().memberId(member.getId())
                .praiseModal(member.getPraiseModal())
                .pointModal(member.getPointModal()).build();
    }
}
