package com.mannavoca.zenga.domain.channel.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.channel.application.dto.request.ChannelCreatingRequestDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@DomainService
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final ChannelCodePublisher channelCodePublisher;

    public Channel getChannelById(Long id) {
        return channelRepository.findById(id).orElseThrow(); // TODO: 에러 처리 로직
    }

    @Transactional
    public Channel createChannel(ChannelCreatingRequestDto channelCreatingRequestDto) {
        Channel newChannel = ChannelMapper.mapChannelCreatingRequestDtoToChannel(channelCreatingRequestDto);
        channelRepository.save(newChannel);
        channelCodePublisher.publishCode(newChannel);

        return newChannel;
    }


    public List<Channel> getAllChannelsByUserId(Long userId) {
        List<Channel> channelList = channelRepository.findAllChannelsByUserId(userId);

        return channelList;
    }
}
