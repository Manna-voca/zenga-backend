package com.mannavoca.zenga.domain.channel.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelResponseDto;
import com.mannavoca.zenga.domain.channel.application.mapper.ChannelMapper;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.mapper.MemberMapper;
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

    public ChannelResponseDto getChannelById(final Long channelId) {
        channelService.validateChannelId(channelId);
        memberService.validateMemberPermissionByUserIdAndChannelId(userUtils.getUser().getId(), channelId);

        return ChannelMapper.mapChannelToChannelResponseDto(channelService.getChannelById(channelId));
    }

    public List<ChannelResponseDto> getAllChannels() {

        return ChannelMapper.mapChannelListToChannelResponseDtoList(channelService.getAllChannelsByUserId(userUtils.getUser().getId()));
    }

    public ChannelResponseDto getChannelByCode(final String code) {
        return ChannelMapper.mapChannelToChannelResponseDto(channelService.getChannelByCode(code));
    }

    public Boolean getChannelValidityById(Long channelId) {
        channelService.validateChannelId(channelId);

        return memberService.countMemberByChannelId(channelId) >= ChannelService.CHANNEL_VALIDITY_MEMBER_COUNT;
    }

    public Slice<MemberInfoResponseDto> searchAllMembersByChannelId(final Long channelId, final Long memberIdCursor, final String keyword, final Pageable pageable){
        channelService.validateChannelId(channelId);
        memberService.validateMemberPermissionByUserIdAndChannelId(userUtils.getUser().getId(), channelId);

        return MemberMapper.MapMemberSliceToMemberInfoResponseDtoList(memberService.findAllMemberSlicesByChannelIdAndKeyword(channelId, memberIdCursor, keyword, pageable));
    }

}
