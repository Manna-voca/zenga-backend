package com.mannavoca.zenga.domain.ranking.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelMemberRankDto {
    List<MemberRankDto> memberRankDtos;

    public static ChannelMemberRankDto create(List<MemberRankDto> memberRankDtos) {
        return new ChannelMemberRankDto(memberRankDtos);
    }
}
