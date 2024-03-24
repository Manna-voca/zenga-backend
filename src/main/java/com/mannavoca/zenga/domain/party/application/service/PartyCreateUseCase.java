package com.mannavoca.zenga.domain.party.application.service;

import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.mannavoca.zenga.common.annotation.DistributedLock;
import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.SecurityUtils;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import com.mannavoca.zenga.domain.party.application.dto.request.ApplyPartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.request.CreatePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.mapper.PartyMapper;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PartyCreateUseCase {
    private final UserUtils userUtils;
    private final MemberService memberService;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;
    private final NotificationService notificationService;

    @Transactional
    public CreatePartyResponseDto createNewParty(CreatePartyRequestDto createPartyRequestDto) {
        memberService.validateChannelMember(SecurityUtils.getUserId(), createPartyRequestDto.getChannelId());

        Member partyMaker = userUtils.getMember(createPartyRequestDto.getChannelId());
        Channel channel = channelService.getChannelById(createPartyRequestDto.getChannelId());

        Party newParty = partyService.createNewParty(createPartyRequestDto, channel);
        participationService.createNewParticipation(true, partyMaker, newParty);

        return PartyMapper.mapToCreatePartyResponseDto(newParty, partyMaker);
    }

    @DistributedLock(key = "'ApplyParty:' + #applyPartyRequestDto.getPartyId() ")
    public void applyParty(ApplyPartyRequestDto applyPartyRequestDto) {
        memberService.validateChannelMember(SecurityUtils.getUserId(), applyPartyRequestDto.getChannelId());

        Member applyMember = userUtils.getMember(applyPartyRequestDto.getChannelId());
        if (participationService.isAlreadyApplied(applyPartyRequestDto.getPartyId(), applyMember.getId())) {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); // TODO: 예외 처리 따로 해야함
        }
        Party party = partyService.getPartyById(applyPartyRequestDto.getPartyId());
        Long participationCount = participationService.getParticipationCountByPartyId(applyPartyRequestDto.getPartyId());
        if (participationCount < party.getMaxCapacity()) {
            participationService.createNewParticipation(false, applyMember, party);
            if (participationCount + 1 == party.getMaxCapacity()) {
                Member maker = party.getParticipationList().stream().filter(Participation::getIsMaker)
                        .collect(Collectors.toList()).get(0).getMember();

                notificationService.createFullPartyNotification(maker, party);
            }
        } else {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); // TODO: 예외 처리 따로 해야함
        }
    }
}
