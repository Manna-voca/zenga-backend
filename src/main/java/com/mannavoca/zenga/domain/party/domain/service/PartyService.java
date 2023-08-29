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

    /**
     * 멤버 ID를 받아서 해당 멤버가 참여한 Party 리스트를 상태별로 2개씩 반환하는 메소드
     * @param memberId 멤버 ID
     * @return Party 리스트
     */
    public List<Party> get2EachPartiesByMemberId(Long memberId) {
        return partyRepository.find2EachPartiesByMemberId(memberId);
    }

    /**
     * 멤버 ID와 상태, 커서, Pageable 객체를 받아서 해당 멤버가 참여한 Party 리스트를 페이징 후 상태별로 반환하고 메소드
     * @param memberId 멤버 ID
     * @param state 상태 - RECRUITING, IN_PROGRESS, COMPLETED
     * @param partyIdCursor 커서
     * @param pageable Pageable 객체
     * @return Party Slice
     */
    public Slice<Party> getPartiesByMemberIdAndState(Long memberId, State state, Long partyIdCursor, Pageable pageable) {
        return partyRepository.findPartiesByMemberIdAndState(memberId, state, partyIdCursor, pageable);
    }

    /**
     * Party ID가 유효한지 검증
     * @param partyId Party ID
     */
    public void validatePartyId(Long partyId) {
        if (!partyRepository.existsById(partyId)) {
            throw BusinessException.of(Error.PARTY_NOT_FOUND);
        }
    }
}
