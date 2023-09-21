package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.application.dto.request.CreatingMemberRequestDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.mapper.MemberMapper;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import com.mannavoca.zenga.domain.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@UseCase
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class MemberCreateUseCase {
    private final MemberService memberService;
    private final ChannelService channelService;
    private final UserUtils userUtils;
    private final NotificationService notificationService;

    public MemberInfoResponseDto createMember(final CreatingMemberRequestDto creatingMemberRequestDto) {
        User user = userUtils.getUser();
        Channel channel = channelService.getChannelById(creatingMemberRequestDto.getChannelId());

        int minimumChannelMemberCount = 10;
        if (channel.getMemberList().size() == minimumChannelMemberCount) {
            channel.getMemberList().forEach(member -> {
                notificationService.createChannelOpenedNotification(member, channel);
            });
        }
        memberService.validateMemberAlreadyExistsByUserIdAndChannelId(user.getId(), channel.getId());

        return MemberMapper.mapMemberToMemberInfoResponseDto(memberService.createMember(user, channel, creatingMemberRequestDto));
    }
}
