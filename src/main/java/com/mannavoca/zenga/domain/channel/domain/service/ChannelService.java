package com.mannavoca.zenga.domain.channel.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
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
     * @throws BusinessException (Error.CHANNEL_NOT_FOUND) Channel을 찾을 수 없을 경우
     */
    public Channel getChannelById(final Long id) {
        return channelRepository.findById(id).orElseThrow(() -> BusinessException.of(Error.CHANNEL_NOT_FOUND));
    }

    /**
     * Channel 생성
     * @param creatingChannelRequestDto Channel 생성 요청 DTO
     * @return 생성된 Channel 객체
     */
    @Transactional
    public Channel createChannel(final CreatingChannelRequestDto creatingChannelRequestDto) {
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
    public List<Channel> getAllChannelsByUserId(final Long userId) {

        return channelRepository.findAllChannelsByUserId(userId);
    }

    /**
     * Channel Code로 Channel 조회
     * @param code Channel Code
     * @return Channel 객체
     * @throws BusinessException (Error.CHANNEL_NOT_FOUND) Channel을 찾을 수 없을 경우
     */
    public Channel getChannelByCode(final String code) {

        return channelRepository.findByCode(code).orElseThrow(() -> BusinessException.of(Error.CHANNEL_NOT_FOUND));
    }

    /** Channel ID의 유효성 검사
     * @param channelId Channel ID
     */
    public void validateChannelId(Long channelId) {
        if(!channelRepository.existsByChannelId(channelId)){
            throw BusinessException.of(Error.CHANNEL_NOT_FOUND);
        }
    }
}
