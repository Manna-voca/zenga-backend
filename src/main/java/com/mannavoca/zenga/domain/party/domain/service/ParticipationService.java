package com.mannavoca.zenga.domain.party.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class ParticipationService {
    private final ParticipationRepository participationRepository;

    public void createNewParticipation(Boolean isMaker, Member member, Party party) {
        participationRepository.save(
                Participation.builder()
                        .isMaker(isMaker)
                        .member(member)
                        .party(party)
                        .build());
    }

    public Map<String, Object> getPartyMakerAndJoinerCount(Long partyId) {
        List<Participation> participationList = participationRepository.findParticipationByParty_Id(partyId);
        Member maker = participationList.stream()
                .filter(Participation::getIsMaker).findFirst()
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND)).getMember();
        return Map.of("maker", maker, "joinerCount", participationList.size());
    }

    public boolean isAlreadyApplied(Long partyId, Long memberId) {
        return participationRepository.existsByParty_IdAndMember_Id(partyId, memberId);
    }

    public void deleteParticipation(Long partyId, Long memberId) {
        participationRepository.deleteByParty_IdAndMember_Id(partyId, memberId);
    }

    public void deleteAllParticipationByPartyId(Long partyId) {
        participationRepository.deleteAllByParty_Id(partyId);
    }

    public Long getParticipationCountByPartyId(Long partyId) {
        return participationRepository.countByParty_Id(partyId);
    }

    public Long getPartyMarkerId(Long partyId) {
        return participationRepository.findPartyMarkerId(partyId);
    }

    public Participation getParticipationByPartyIdAndMemberId(Long partyId, Long memberId) {
        return participationRepository.findByParty_IdAndMember_Id(partyId, memberId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
}
