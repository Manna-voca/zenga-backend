package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelAndMemberIdResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelOwnershipInfoResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelValidityResponseDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberListInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.mapper.MemberMapper;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class ChannelReadUseCase {
    private final ChannelService channelService;
    private final MemberService memberService;
    private final UserUtils userUtils;

    public ChannelOwnershipInfoResponseDto getChannelById(final Long channelId) {
        channelService.validateChannelId(channelId);
        memberService.validateMemberPermissionByUserIdAndChannelId(userUtils.getUser().getId(), channelId);
        Boolean isOwner = userUtils.getMember(channelId).getLevel().equals(LevelType.MAINTAINER);

        return ChannelMapper.mapChannelToChannelOwnershipInfoResponseDto(channelService.getChannelById(channelId), isOwner);
    }

    public List<ChannelAndMemberIdResponseDto> getAllChannels() {

        return channelService.getAllChannelsIncludingMemberIdByUserId(userUtils.getUser().getId());
    }

    public ChannelResponseDto getChannelByCode(final String code) {
        Channel channel = channelService.getChannelByCode(code);
//        Boolean isOwner = userUtils.getMember(channel.getId()).getLevel().equals(LevelType.MAINTAINER);
        return ChannelMapper.mapChannelToChannelResponseDto(channel);
    }

    public ChannelValidityResponseDto getChannelValidityById(final Long channelId) {
        channelService.validateChannelId(channelId);

        final Long memberCount = memberService.countMemberByChannelId(channelId);

        return ChannelValidityResponseDto
                .builder()
                .isValid(memberCount >= ChannelService.CHANNEL_VALIDITY_MEMBER_COUNT)
                .memberCount(memberCount)
                .build();
    }

    public MemberListInfoResponseDto searchAllMembersByChannelId(final Long channelId, final Long memberIdCursor, final String keyword, final Pageable pageable){
        channelService.validateChannelId(channelId);
        if (memberIdCursor != null) {
            memberService.validateMemberId(memberIdCursor);
        }
        memberService.validateMemberPermissionByUserIdAndChannelId(userUtils.getUser().getId(), channelId);

        return MemberMapper.mapMemberSliceToMemberListInfoResponseDto(memberService.findAllMemberSlicesByChannelIdAndKeyword(channelId, memberIdCursor, keyword, pageable));
    }

}
