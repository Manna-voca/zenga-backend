package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.application.dto.request.CreatingMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.mapper.MemberMapper;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import com.mannavoca.zenga.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class MemberCreateUseCase {
    private final MemberService memberService;
    private final ChannelService channelService;
    private final UserUtils userUtils;

    public MemberInfoResponseDto createMember(CreatingMemberRequestDto creatingMemberRequestDto) {
        User user = userUtils.getUser();
        Channel channel = channelService.getChannelById(creatingMemberRequestDto.getChannelId());

        return MemberMapper.mapMemberToMemberInfoResponseDto(memberService.createMember(user, channel, creatingMemberRequestDto));
    }
}
