package com.mannavoca.zenga.domain.channel.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.channel.application.dto.request.ChannelCreatingRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class ChannelMapper {
    public static ChannelResponseDto mapChannelToChannelResponseDto(Channel channel) {
        return ChannelResponseDto.builder()
                .id(channel.getId())
                .name(channel.getName())
//                .capacity(channel.getCapacity())
                .logoImageUrl(channel.getLogoImageUrl())
                .description(channel.getDescription())
//                .finishedAt(channel.getFinishedAt())
                .code(channel.getCode())
                .build();
    }

    public static Channel mapChannelCreatingRequestDtoToChannel(ChannelCreatingRequestDto channelCreatingRequestDto) {
        return Channel.builder()
                .name(channelCreatingRequestDto.getName())
//                .capacity(channelCreatingRequestDto.getCapacity())
                .logoImageUrl(channelCreatingRequestDto.getLogoImageUrl())
                .description(channelCreatingRequestDto.getDescription())
//                .finishedAt(channelCreatingRequestDto.getFinishedAt())
                .build();
    }

    public static List<ChannelResponseDto> mapChannelListToChannelResponseDtoList(List<Channel> channelList) {
        return channelList.stream()
                .map(ChannelMapper::mapChannelToChannelResponseDto)
                .collect(Collectors.toList());
    }
}
