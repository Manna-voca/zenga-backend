package com.mannavoca.zenga.domain.member.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
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
    private final PartyService partyService;
    private final ParticipationService participationService;

    public Slice<PartyTapResponseDto> getPartyListByMemberId(Long memberId, State state, Long partyIdCursor, Pageable pageable) {
        Slice<Party> partySlice = partyService.getPartiesByMemberIdAndState(memberId, state, partyIdCursor, pageable);

        Slice<PartyTapResponseDto> partyTapResponseDtoSlice = partySlice.map(party -> {
            Map<String, Object> partyMakerAndJoinerCount = participationService.getPartyMakerAndJoinerCount(party.getId());
            Member partyMaker = (Member) partyMakerAndJoinerCount.get("maker");
            Integer joinMemberCount = (Integer) partyMakerAndJoinerCount.get("joinerCount");
            return PartyMapper.mapToPartyTapResponseDto(party, partyMaker, joinMemberCount);
        });

        return partyTapResponseDtoSlice;
    }

    public List<PartyTapResponseDto> getAll2PartyListByMemberId(Long memberId) {
        List<Party> partyList = partyService.get2EachPartiesByMemberId(memberId);
        return partyList.stream().map(party -> {
            Map<String, Object> partyMakerAndJoinerCount = participationService.getPartyMakerAndJoinerCount(party.getId());
            Member partyMaker = (Member) partyMakerAndJoinerCount.get("maker");
            Integer joinMemberCount = (Integer) partyMakerAndJoinerCount.get("joinerCount");
            return PartyMapper.mapToPartyTapResponseDto(party, partyMaker, joinMemberCount);}).collect(Collectors.toList());
    }
}
