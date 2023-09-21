package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@RequiredArgsConstructor
@Slf4j
@UseCase
@Transactional(propagation = REQUIRES_NEW)
public class ChannelDeleteUseCase {
    private final ChannelService channelService;
    private final UserUtils userUtils;

    public void deleteChannel(final Long channelId) {
        channelService.validateChannelId(channelId);
        Member member = userUtils.getMember(channelId);
        if (!member.getLevel().equals(LevelType.MAINTAINER)) {
            throw BusinessException.of(Error.NOT_MAINTAINER_OF_CHANNEL);
        }

        channelService.deleteChannelById(channelId);
    }
}
