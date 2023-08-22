package com.mannavoca.zenga.domain.channel.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.channel.application.dto.request.CreatingChannelRequestDto;
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

    /**
     * Channel ID로 Channel 조회
     * @param id Channel ID
     * @return Channel 객체
     * TODO: 에러 처리 구현 필요
     */
    public Channel getChannelById(Long id) {
        return channelRepository.findById(id).orElseThrow();
    }

    /**
     * Channel 생성
     * @param creatingChannelRequestDto Channel 생성 요청 DTO
     * @return 생성된 Channel 객체
     */
    @Transactional
    public Channel createChannel(CreatingChannelRequestDto creatingChannelRequestDto) {
        Channel newChannel = ChannelMapper.mapCreatingChannelRequestDtoToChannel(creatingChannelRequestDto);
        channelRepository.save(newChannel);
        channelCodePublisher.publishCode(newChannel);

        return newChannel;
    }


    /**
     * User ID로 Channel 조회
     * @param userId User ID
     * @return Channel 리스트
     */
    public List<Channel> getAllChannelsByUserId(Long userId) {
        List<Channel> channelList = channelRepository.findAllChannelsByUserId(userId);

        return channelList;
    }

    /**
     * Channel Code로 Channel 조회
     * @param code Channel Code
     * @return Channel 객체
     * TODO: 에러 처리 구현 필요
     */
    public Channel getChannelByCode(String code) {
        Channel channel = channelRepository.findByCode(code).orElseThrow();

        return channel;
    }
}
