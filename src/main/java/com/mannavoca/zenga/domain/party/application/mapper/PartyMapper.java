package com.mannavoca.zenga.domain.party.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.domain.entity.Party;

import java.util.List;

@Mapper
public class PartyMapper {

    public static CreatePartyResponseDto mapToCreatePartyResponseDto(Party party, Member partyMaker) {
        CreatePartyResponseDto.JoinMemberInfo joinMemberInfo = CreatePartyResponseDto.JoinMemberInfo.builder().memberId(partyMaker.getId())
                .memberName(partyMaker.getNickname()).memberProfileImageUrl(partyMaker.getProfileImageUrl())
                .build();
        return CreatePartyResponseDto.builder()
                .partyId(party.getId())
                .title(party.getTitle()).content(party.getContent())
                .maxCapacity(party.getMaxCapacity()).location(party.getLocation())
                .partyDate(party.getPartyDate()).partyImageUrl(party.getPartyImageUrl())
                .createdAt(party.getCreatedDate())
                .openMemberId(partyMaker.getId()).openMemberName(partyMaker.getNickname()).openMemberProfileImageUrl(partyMaker.getProfileImageUrl())
                .joinMemberInfo(List.of(joinMemberInfo))
                .build();
    }
}
