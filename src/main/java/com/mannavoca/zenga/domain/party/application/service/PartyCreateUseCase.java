package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.service.ChannelService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.application.dto.request.CreatePartyRequestDto;
import com.mannavoca.zenga.domain.party.application.dto.response.CreatePartyResponseDto;
import com.mannavoca.zenga.domain.party.application.mapper.PartyMapper;
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
public class PartyCreateUseCase {
    private final UserUtils userUtils;
    private final ChannelService channelService;
    private final PartyService partyService;
    private final ParticipationService participationService;

    public CreatePartyResponseDto createNewParty(CreatePartyRequestDto createPartyRequestDto) {
        Member partyMaker = userUtils.getMember(createPartyRequestDto.getChannelId());
        Channel channel = channelService.getChannelById(createPartyRequestDto.getChannelId());

        Party newParty = partyService.createNewParty(createPartyRequestDto, channel);
        participationService.createNewParticipation(true, partyMaker, newParty);

        return PartyMapper.mapToCreatePartyResponseDto(newParty, partyMaker);
    }

    public void applyParty(Long channelId, Long partyId) {
        Member applyMember = userUtils.getMember(channelId);
        if (participationService.isAlreadyApplied(applyMember.getId(), partyId)) {
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); // TODO: 예외 처리 따로 해야함
        }
        Party party = partyService.getPartyById(partyId);
        participationService.createNewParticipation(false, applyMember, party);
    }
}
