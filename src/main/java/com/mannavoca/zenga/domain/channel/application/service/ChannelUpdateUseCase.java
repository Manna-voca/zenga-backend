package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.channel.application.dto.request.UpdatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class ChannelUpdateUseCase {
    private final ChannelService channelService;

    public ChannelResponseDto updateChannel(final Long channelId, final UpdatingChannelRequestDto updatingChannelRequestDto) {
        Channel channel = channelService.getChannelById(channelId);

        return ChannelMapper.mapChannelToChannelResponseDto(channelService.updateChannel(channel, updatingChannelRequestDto));
    }
}
