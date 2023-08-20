package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.user.domain.service.UserFindService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class ChannelReadUseCase {
    private final ChannelService channelService;
    private final UserFindService userFindService;

    public ChannelResponseDto getChannelById(Long channelId) {
        return ChannelMapper.mapChannelToChannelResponseDto(channelService.getChannelById(channelId));
    }

    public List<ChannelResponseDto> getAllChannelsByUserId(Long userId) {
        userFindService.validateUserId(userId);

        return ChannelMapper.mapChannelListToChannelResponseDtoList(channelService.getAllChannelsByUserId(userId));
    }

    public ChannelResponseDto getChannelByCode(String code) {
        return ChannelMapper.mapChannelToChannelResponseDto(channelService.getChannelByCode(code));
    }
}
