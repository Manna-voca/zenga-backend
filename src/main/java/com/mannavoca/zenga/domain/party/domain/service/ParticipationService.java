package com.mannavoca.zenga.domain.party.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.album.application.dto.request.GetAlbumDetailRequestDto;
import com.mannavoca.zenga.domain.album.application.dto.request.GetAlbumListRequestDto;
import com.mannavoca.zenga.domain.album.application.dto.response.GetAlbumDetailResponseDto;
import com.mannavoca.zenga.domain.album.application.dto.response.GetAlbumListResponseDto;
import com.mannavoca.zenga.domain.album.application.dto.response.GetParticipationWithResponseDto;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import com.mannavoca.zenga.domain.party.domain.repository.ParticipationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    private final MemberRepository memberRepository;

    public void createNewParticipation(Boolean isMaker, Member member, Party party) {
        Participation participation = participationRepository.save(
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

    public Long getParticipationCountByMemberId(Long memberId) {
        return participationRepository.countByMember_Id(memberId);
    }

    public Long getPartyMarkerId(Long partyId) {
        return participationRepository.findPartyMarkerId(partyId);
    }

    public Participation getParticipationByPartyIdAndMemberId(Long partyId, Long memberId) {
        return participationRepository.findByParty_IdAndMember_Id(partyId, memberId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
  
    public GetAlbumListResponseDto getAlbumList(GetAlbumListRequestDto getAlbumListRequestDto) {
        Long memberId = getAlbumListRequestDto.getMemberId();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        List<Participation> participationList = participationRepository.findAllByMemberAndAlbumCreatedDateIsNotNullOrderByAlbumCreatedDateDesc(member);

        List<GetAlbumListResponseDto.AlbumResponseDto> albumResponseDtoList = participationList.stream()
                .map(participation -> GetAlbumListResponseDto.AlbumResponseDto.of(
                        participation.getId(),
                        participation.getParty().getCardImageUrl(),
                        participation.getAlbumCreatedDate(),
                        participation.getParty().getTitle(),
                        participation.getParty().getContent()
                        )
                ).collect(Collectors.toList());

        return GetAlbumListResponseDto.of(albumResponseDtoList);
    }


    public GetAlbumDetailResponseDto getAlbumDetail(GetAlbumDetailRequestDto getAlbumDetailRequestDto) {
        Long participationId = getAlbumDetailRequestDto.getParticipationId();

        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
        Party party = participation.getParty();

        return GetAlbumDetailResponseDto.of(participation.getId(), party.getCardImageUrl(), party.getTitle(), party.getContent());
    }

    public GetParticipationWithResponseDto getParticipationListWith(Long participationId) {

        Participation participation = participationRepository.findById(participationId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));

        List<Member> memberList = participationRepository.findParticipationByParty_Id(participation.getParty().getId())
                .stream().map(Participation::getMember).collect(Collectors.toList());

        return GetParticipationWithResponseDto.of(memberList.stream().map(GetParticipationWithResponseDto.ParticipationWithResponseDto::of).collect(Collectors.toList()));
    }

    @Transactional
    public void setParticipationAlbumCreatedDate(Participation participation) {
        participation.setAlbumCreatedDate(LocalDateTime.now());
    }
}
