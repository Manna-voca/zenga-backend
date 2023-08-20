package com.mannavoca.zenga.domain.praise.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class PraiseMapper {

    public static CurrentTodoPraiseResponseDto mapToCurrentTodoPraiseResponseDto(MemberPraise todayTodoPraiseForMember, List<Member> randomMembersByChannelId) {
        List<CurrentTodoPraiseResponseDto.MemberInfoList> memberInfoLists = randomMembersByChannelId.stream()
                .map(member -> CurrentTodoPraiseResponseDto.MemberInfoList.builder()
                        .memberId(member.getId())
                        .name(member.getNickname())
                        .profileImageUrl(member.getProfileImageUrl())
                        .build()
                ).collect(Collectors.toList());
        return CurrentTodoPraiseResponseDto.builder()
                .praise(todayTodoPraiseForMember.getPraise().getDescription())
                .memberList(memberInfoLists)
                .build();
    }
}
