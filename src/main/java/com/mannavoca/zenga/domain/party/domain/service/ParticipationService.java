package com.mannavoca.zenga.domain.party.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;

    public Participation createNewParticipation(Boolean isMaker, Member member, Party party) {
        return participationRepository.save(
                Participation.builder()
                        .isMaker(isMaker)
                        .member(member)
                        .party(party)
                        .build());
    }
}
