package com.mannavoca.zenga.domain.party.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.party.application.dto.request.CreatePartyRequestDto;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.PartyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
                    .partyImageUrl(createPartyRequestDto.getPartyImageUrl()).channel(channel).build());
    }
}
