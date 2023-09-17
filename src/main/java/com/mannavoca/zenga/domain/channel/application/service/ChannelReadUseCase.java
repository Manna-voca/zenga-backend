package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.application.dto.request.SearchChannelMemberRequestDto;
import com.mannavoca.zenga.domain.channel.application.dto.response.*;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
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

    public Slice<MemberInfoResponseDto> searchAllMembersByChannelId(final Long channelId, final SearchChannelMemberRequestDto searchChannelMemberRequestDto, final Pageable pageable){
        channelService.validateChannelId(channelId);
        final Long cursorId = searchChannelMemberRequestDto.getCursorId();

        if (cursorId != null) {
            memberService.validateMemberId(cursorId);
        }
        memberService.validateMemberPermissionByUserIdAndChannelId(userUtils.getUser().getId(), channelId);

        return MemberMapper.MapMemberSliceToMemberInfoResponseDtoList(memberService.findAllMemberSlicesByChannelIdAndKeyword(channelId, searchChannelMemberRequestDto.getCursorId(), searchChannelMemberRequestDto.getCursorName(),  searchChannelMemberRequestDto.getKeyword(), pageable));
    }

    public ChannelMemberCountResponseDto getChannelMemberCount(final Long channelId) {
        channelService.validateChannelId(channelId);
        memberService.validateMemberPermissionByUserIdAndChannelId(userUtils.getUser().getId(), channelId);

        return ChannelMemberCountResponseDto.builder()
                .count(memberService.countMemberByChannelId(channelId))
                .build();
    }
}
