package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class PartyDeleteUseCase {
    private final UserUtils userUtils;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;


    public void cancelParty(Long channelId, Long partyId) {
        Member member = userUtils.getMember(channelId);
        Party party = partyService.getPartyById(partyId);

        checkIsPartyMaker(member, party);

        participationService.deleteAllParticipationByPartyId(partyId);
        partyService.deletePartyById(partyId);
    }

    public void applyCancelParty(Long channelId, Long partyId) {
        Member applyCancelMember = userUtils.getMember(channelId);
        if (!participationService.isAlreadyApplied(applyCancelMember.getId(), partyId)) {
            // TODO: 예외 처리 따로 해야함, 등록 정보가 없는 것
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
        }

        checkIsPartyMaker(applyCancelMember, partyService.getPartyById(partyId));

        participationService.deleteParticipation(partyId, applyCancelMember.getId());
    }

    private void checkIsPartyMaker(Member member, Party party) {
        Member partyMaker = party.getParticipationList().stream()
                .filter(Participation::getIsMaker)
                .findFirst()
                .map(Participation::getMember).orElseGet(() -> null);
        if (partyMaker == null || !partyMaker.equals(member)) {
            throw BusinessException.of(Error.DATA_NOT_FOUND); // TODO: 예외처리 따로 정의
        }
    }
}
