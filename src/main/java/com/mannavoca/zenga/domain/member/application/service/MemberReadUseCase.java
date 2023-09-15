package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockCountInfoListResponseDto;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockCountResponseDto;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockCountResponseInterface;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockInfoResponseDto;
import com.mannavoca.zenga.domain.block.domain.service.BlockService;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.application.mapper.BlockMapper;
import com.mannavoca.zenga.domain.member.application.mapper.MemberMapper;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyTapIncludingStateResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.PartyTapResponseDto;
import com.mannavoca.zenga.domain.party.application.mapper.PartyMapper;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@UseCase
public class MemberReadUseCase {
    private final MemberService memberService;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;
    private final BlockService blockService;
    private final UserUtils userUtils;

    public Slice<PartyTapResponseDto> getPartyListByMemberId(final Long memberId, final State state, final Long partyIdCursor, final Pageable pageable) {
        memberService.validateMemberId(memberId);
        if (partyIdCursor != null) {
            partyService.validatePartyId(partyIdCursor);
        }

        Slice<Party> partySlice = partyService.getPartiesByMemberIdAndState(memberId, state, partyIdCursor, pageable);

        Slice<PartyTapResponseDto> partyTapResponseDtoSlice = partySlice.map(party -> {
            Map<String, Object> partyMakerAndJoinerCount = participationService.getPartyMakerAndJoinerCount(party.getId());
            Member partyMaker = (Member) partyMakerAndJoinerCount.get("maker");
            Integer joinMemberCount = (Integer) partyMakerAndJoinerCount.get("joinerCount");
            return PartyMapper.mapToPartyTapResponseDto(party, partyMaker, joinMemberCount);
        });

        return partyTapResponseDtoSlice;
    }

    public List<PartyTapIncludingStateResponseDto> getAll2PartyListByMemberId(final Long memberId) {
        memberService.validateMemberId(memberId);
        List<Party> partyList = partyService.get2EachPartiesByMemberId(memberId);

        return partyList.stream().map(party -> {
            Map<String, Object> partyMakerAndJoinerCount = participationService.getPartyMakerAndJoinerCount(party.getId());
            Member partyMaker = (Member) partyMakerAndJoinerCount.get("maker");
            Integer joinMemberCount = (Integer) partyMakerAndJoinerCount.get("joinerCount");
            return PartyMapper.mapPartyToPartyTapIncludingStateResponseDto(party, partyMaker, joinMemberCount);}
        ).collect(Collectors.toList());
    }

    public MemberInfoResponseDto getMemberInfoByChannelId(final Long channelId) {
        channelService.validateChannelId(channelId);
        final Member member = memberService.findMemberByUserId(userUtils.getUser().getId(), channelId);

        return MemberMapper.mapMemberToMemberInfoResponseDto(member);
    }

    public List<MemberInfoResponseDto> getMemberInfoList() {
        return MemberMapper.mapMemberListToMemberInfoResponseDtoList(memberService.getMemberListByUserId(userUtils.getUser().getId()));
    }

    public BlockCountInfoListResponseDto getAllBlocksAndCountsByMemberId(final Long memberId) {
        List<BlockCountResponseInterface> blockCountResponseDtoList = blockService.findAllBlockCountListByMemberId(memberId);
        System.out.println(blockCountResponseDtoList.get(0).getBlockType());
        List<BlockInfoResponseDto> blockInfoResponseDtoList = blockService.findAllByMemberId(memberId);

        return BlockCountInfoListResponseDto
                .builder()
                .blockCountResponseDtoList(blockCountResponseDtoList)
                .blockInfoResponseDtoList(blockInfoResponseDtoList)
                .build();
    }

    public MemberInfoResponseDto getMemberInfoByMemberId(final Long memberId) {
        return MemberMapper.mapMemberToMemberInfoResponseDto(memberService.findMemberById(memberId));
    }
}
