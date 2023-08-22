package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.application.dto.request.CompletePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.EditPartyInfoRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CompletePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.mapper.PartyMapper;
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
public class PartyUpdateUseCase {
    private final UserUtils userUtils;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;

    public CreatePartyResponseDto editPartyInfo(Long channelId, EditPartyInfoRequestDto editPartyInfoRequestDto) {
        Member member = userUtils.getMember(channelId);
        Party party = partyService.getPartyById(editPartyInfoRequestDto.getPartyId());

        checkIsPartyMaker(member, party);

        if (editPartyInfoRequestDto.getMaxCapacity() < participationService.getParticipationCountByPartyId(party.getId())) {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); // TODO: 예외처리 따로 정의
        }

        Party updatedParty = partyService.updatePartyInfo(party, editPartyInfoRequestDto);
        return PartyMapper.mapToCreatePartyResponseDto(updatedParty, member);
    }

    public CompletePartyResponseDto uploadPartyCardAndComplete(Long channelId, CompletePartyRequestDto completePartyRequestDto) {
        Member member = userUtils.getMember(channelId);
        Party party = partyService.getPartyById(completePartyRequestDto.getPartyId());

        checkIsPartyMaker(member, party);

        Party completedPartyAndUploadCard = partyService.completePartyAndUploadCard(party, completePartyRequestDto.getPartyCardImageUrl());
        return PartyMapper.mapToCompletePartyResponseDto(completedPartyAndUploadCard);
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
