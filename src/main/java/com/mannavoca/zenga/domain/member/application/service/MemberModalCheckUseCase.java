package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberModalPermitResponseDto;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class MemberModalCheckUseCase {
    private final UserUtils userUtils;
    private final MemberService memberService;

    public MemberModalPermitResponseDto getMemberModalPermit(Long channelId) {
        Member member = userUtils.getMember(channelId);
        return MemberModalPermitResponseDto.builder().memberId(member.getId())
                .praiseModal(member.getPraiseModal())
                .pointModal(member.getPointModal()).build();
    }

    @Transactional
    public MemberModalPermitResponseDto changePointModalStatus(Long channelId) {
        Member member = memberService.updatePointModal(userUtils.getMember(channelId));
        return MemberModalPermitResponseDto.builder().memberId(member.getId())
                .praiseModal(member.getPraiseModal())
                .pointModal(member.getPointModal()).build();
    }

    @Transactional
    public MemberModalPermitResponseDto changePraiseModalStatus(Long channelId) {
        Member member = memberService.updatePraiseModal(userUtils.getMember(channelId));
        return MemberModalPermitResponseDto.builder().memberId(member.getId())
                .praiseModal(member.getPraiseModal())
                .pointModal(member.getPointModal()).build();
    }
}
