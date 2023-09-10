package com.mannavoca.zenga.domain.channel.domain.repository;

import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelAndMemberIdResponseDto;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;

import java.util.List;

public interface ChannelRepositoryCustom {
    List<Channel> findAllChannelsByUserId(Long userId);

    boolean existsByChannelId(Long channelId);

    List<ChannelAndMemberIdResponseDto> findAllChannelsIncludingMemberIdByUserId(Long userId);
}
