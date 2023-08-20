package com.mannavoca.zenga.domain.praise.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.mapper.PraiseMapper;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.service.MemberPraiseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PraiseSearchUseCase {
    private final UserUtils userUtils;
    private final MemberPraiseService memberPraiseService;
    private final MemberService memberService;

    public CurrentTodoPraiseResponseDto getCurrentTodoPraiseAndMemberList(Long channelId) {
        Member member = userUtils.getMember(channelId);
        Optional<MemberPraise> todayTodoPraiseOpt = memberPraiseService.findTodayTodoPraiseForMember(member.getId());

        return todayTodoPraiseOpt
                .map(todayTodoPraise -> {
                    List<Member> randomMembersByChannelId = memberService.find4RandomMembersByChannelId(member.getId(), channelId);
                    return PraiseMapper.mapToCurrentTodoPraiseResponseDto(todayTodoPraise, randomMembersByChannelId);
                })
                .orElse(null);
    }
}
