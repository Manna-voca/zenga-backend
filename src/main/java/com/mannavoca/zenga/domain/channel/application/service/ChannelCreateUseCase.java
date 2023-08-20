package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.channel.application.dto.request.CreatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class ChannelCreateUseCase {
    private final ChannelService channelService;

    public ChannelResponseDto createChannel(CreatingChannelRequestDto creatingChannelRequestDto) {
        return ChannelMapper.mapChannelToChannelResponseDto(channelService.createChannel(creatingChannelRequestDto));
    }

}
