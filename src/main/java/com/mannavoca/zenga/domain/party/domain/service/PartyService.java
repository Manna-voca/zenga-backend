package com.mannavoca.zenga.domain.party.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import com.mannavoca.zenga.domain.party.application.dto.request.CreatePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.EditPartyInfoRequestDto;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class PartyService {
    private final PartyRepository partyRepository;

    public Party createNewParty(CreatePartyRequestDto createPartyRequestDto, Channel channel) {
        return partyRepository.save(
                Party.builder()
                    .title(createPartyRequestDto.getTitle())
                    .content(createPartyRequestDto.getContent())
                    .maxCapacity(createPartyRequestDto.getMaxCapacity())
                    .location(createPartyRequestDto.getLocation())
                    .partyDate(createPartyRequestDto.getPartyDate())
                    .partyImageUrl(createPartyRequestDto.getPartyImageUrl()).cardImageUrl(null)
                        .channel(channel).isOpen(true).build());
    }

    public Slice<Party> getPartyListByChannelId(Long channelId, Long partyId, Pageable pageable) {
        return partyRepository.findPartyListByChannelId(channelId, partyId, pageable);
    }

    public Party getPartyById(Long partyId) {
        return partyRepository.findById(partyId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }

    public Party completePartyAndUploadCard(Party party, String partyCardImageUrl) {
        party.updateCardImageUrl(partyCardImageUrl);
        return partyRepository.save(party);
    }

    public void deletePartyById(Long partyId) {
        partyRepository.deleteById(partyId);
    }

    public Party updatePartyInfo(Party party, EditPartyInfoRequestDto editPartyInfoRequestDto) {
        party.editPartyInfo(
                editPartyInfoRequestDto.getTitle(),
                editPartyInfoRequestDto.getContent(),
                editPartyInfoRequestDto.getMaxCapacity(),
                editPartyInfoRequestDto.getLocation(),
                editPartyInfoRequestDto.getPartyDate(),
                editPartyInfoRequestDto.getPartyImageUrl());
        return partyRepository.save(party);
    }

    public List<Party> get2EachPartiesByMemberId(Long memberId) {
        return partyRepository.find2EachPartiesByMemberId(memberId);
    }

    public Slice<Party> getPartiesByMemberIdAndState(Long memberId, State state, Long partyIdCursor, Pageable pageable) {
        return partyRepository.findPartiesByMemberIdAndState(memberId, state, partyIdCursor, pageable);
    }
}
