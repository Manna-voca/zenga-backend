package com.mannavoca.zenga.domain.channel.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.channel.application.dto.request.CreatingChannelRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelOwnershipInfoResponseDto;
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
//                .description(channel.getDescription())
//                .finishedAt(channel.getFinishedAt())
                .code(channel.getCode())
                .build();
    }

    public static Channel mapCreatingChannelRequestDtoToChannel(CreatingChannelRequestDto creatingChannelRequestDto) {
        return Channel.builder()
                .name(creatingChannelRequestDto.getName())
//                .capacity(creatingChannelRequestDto.getCapacity())
                .logoImageUrl(creatingChannelRequestDto.getLogoImageUrl())
//                .description(creatingChannelRequestDto.getDescription())
//                .finishedAt(creatingChannelRequestDto.getFinishedAt())
                .build();
    }

    public static List<ChannelResponseDto> mapChannelListToChannelResponseDtoList(List<Channel> channelList) {
        return channelList.stream()
                .map(ChannelMapper::mapChannelToChannelResponseDto)
                .collect(Collectors.toList());
    }

    public static ChannelOwnershipInfoResponseDto mapChannelToChannelOwnershipInfoResponseDto(Channel channel, Boolean isOwner) {
        return ChannelOwnershipInfoResponseDto.builder()
                .id(channel.getId())
                .name(channel.getName())
                .logoImageUrl(channel.getLogoImageUrl())
                .code(channel.getCode())
                .isOwner(isOwner)
                .build();
    }
}
