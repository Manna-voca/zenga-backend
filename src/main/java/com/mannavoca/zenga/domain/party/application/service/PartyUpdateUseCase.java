package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import com.mannavoca.zenga.domain.party.application.dto.request.ClosePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.CompletePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.EditPartyInfoRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CompletePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.mapper.PartyMapper;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;
import com.mannavoca.zenga.domain.point.application.service.PointPolicyUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class PartyUpdateUseCase {
    private final UserUtils userUtils;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;
    private final PointPolicyUseCase pointPolicyUseCase;
    private final NotificationService notificationService;
    private final PartyUpdateEventListener partyUpdateEventListener;

    public CreatePartyResponseDto editPartyInfo(EditPartyInfoRequestDto editPartyInfoRequestDto) {
        Member member = userUtils.getMember(editPartyInfoRequestDto.getChannelId());
        Party party = partyService.getPartyById(editPartyInfoRequestDto.getPartyId());

        checkIsPartyMaker(member, party);

        if (editPartyInfoRequestDto.getMaxCapacity() < participationService.getParticipationCountByPartyId(party.getId())) {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); // TODO: 예외처리 따로 정의
        }

        Party updatedParty = partyService.updatePartyInfo(party, editPartyInfoRequestDto);
        return PartyMapper.mapToCreatePartyResponseDto(updatedParty, member);
    }

    public CreatePartyResponseDto closeParty(ClosePartyRequestDto closePartyRequestDto) {
        Member member = userUtils.getMember(closePartyRequestDto.getChannelId());
        Party party = partyService.getPartyById(closePartyRequestDto.getPartyId());

        checkIsPartyMaker(member, party);

        Party updatedParty = partyService.closeParty(party);

        notificationService.createCardMakingNotification(member, updatedParty);
        return PartyMapper.mapToCreatePartyResponseDto(updatedParty, member);
    }

    public CompletePartyResponseDto uploadPartyCardAndComplete(CompletePartyRequestDto completePartyRequestDto) {
        Member member = userUtils.getMember(completePartyRequestDto.getChannelId());
        Party party = partyService.getPartyById(completePartyRequestDto.getPartyId());

        checkIsPartyMaker(member, party);

        Party completedPartyAndUploadCard = partyService.completePartyAndUploadCard(party, completePartyRequestDto.getPartyCardImageUrl());

        List<Participation> particiationList =  party.getParticipationList();
        List<Member> participationMemberList = particiationList.stream().map(Participation::getMember).collect(Collectors.toList());
        pointPolicyUseCase.accumulatePointByParty(participationMemberList, party);
        particiationList.forEach(partyMember -> partyUpdateEventListener.checkPartyCountAndUpdateMemberBlock(partyMember.getMember().getId()));

        particiationList.forEach(
                participationService::setParticipationAlbumCreatedDate
        );

        participationMemberList.forEach(
                participationMember -> notificationService.createCardNotification(participationMember, completedPartyAndUploadCard)
        );
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
