package com.mannavoca.zenga.domain.channel.domain.service;

import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Slf4j
@Component
public class ChannelCodePublisher {
    private final ChannelRepository channelRepository;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void publishCode(Channel channel) {
        channel.setCode(UniqueCodeGenerator.generateCodeForChannel(channel.getId()));
        channelRepository.save(channel);
    }
}
