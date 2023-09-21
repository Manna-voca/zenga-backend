package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.application.dto.request.UpdatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@UseCase
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ChannelUpdateUseCase {
    private final ChannelService channelService;
    private final MemberService memberService;
    private final UserUtils userUtils;

    public ChannelResponseDto updateChannel(final Long channelId, final UpdatingChannelRequestDto updatingChannelRequestDto) {
        Channel channel = channelService.getChannelById(channelId);
        Member currentMember = userUtils.getMember(channelId);
        if (!currentMember.getLevel().equals(LevelType.MAINTAINER)){
            throw BusinessException.of(Error.NOT_MAINTAINER_OF_CHANNEL);
        }

        return ChannelMapper.mapChannelToChannelResponseDto(channelService.updateChannel(channel, updatingChannelRequestDto));
    }
}
