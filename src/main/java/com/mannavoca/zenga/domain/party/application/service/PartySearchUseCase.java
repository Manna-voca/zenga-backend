package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.comment.domain.service.CommentService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyDetailInfoResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyTapResponseDto;
import com.mannavoca.zenga.domain.party.application.mapper.PartyMapper;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartySearchUseCase {
    private final UserUtils userUtils;
    private final MemberService memberService;
    private final CommentService commentService;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;

    public SliceResponse<PartyTapResponseDto> searchPartyListInChannel(Long channelId, Long partyId, Pageable pageable) {
        Slice<Party> partyListByChannelId = partyService.getPartyListByChannelId(channelId, partyId, pageable);

        Slice<PartyTapResponseDto> partyTapResponseDtoSlice = partyListByChannelId.map(party -> {
            Map<String, Object> partyMakerAndJoinerCount = participationService.getPartyMakerAndJoinerCount(party.getId());
            Member partyMaker = (Member) partyMakerAndJoinerCount.get("maker");
            Integer joinMemberCount = (Integer) partyMakerAndJoinerCount.get("joinerCount");
            return PartyMapper.mapToPartyTapResponseDto(party, partyMaker, joinMemberCount);
        });
        return SliceResponse.of(partyTapResponseDtoSlice);
    }

    public PartyDetailInfoResponseDto getPartyDetailInfo(Long partyId, Long channelId) {
        Member member = userUtils.getMember(channelId);
        Party party = partyService.getPartyById(partyId);
        Long channelMakerId = memberService.getChannelMaker(channelId).getId();
        Long partyCommentCount = commentService.partyCommentCount(partyId);
        Comment lastComment = commentService.findLastCommentInParty(partyId);
        return PartyMapper.mapToPartyDetailInfoResponseDto(party, member, channelMakerId, partyCommentCount, lastComment);
    }
}
