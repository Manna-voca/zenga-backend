package com.mannavoca.zenga.domain.party.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import com.mannavoca.zenga.domain.party.application.dto.response.*;
import com.mannavoca.zenga.domain.party.domain.entity.Participation;
import com.mannavoca.zenga.domain.party.domain.entity.Party;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Mapper
public class PartyMapper {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M월d일 (E) HH:mm", Locale.KOREAN);


    public static CreatePartyResponseDto mapToCreatePartyResponseDto(Party party, Member partyMaker) {
        CreatePartyResponseDto.JoinMemberInfo joinMemberInfo = CreatePartyResponseDto.JoinMemberInfo.builder().memberId(partyMaker.getId())
                .memberName(partyMaker.getNickname()).memberProfileImageUrl(partyMaker.getProfileImageUrl())
                .build();
        return CreatePartyResponseDto.builder()
                .partyId(party.getId())
                .title(party.getTitle()).content(party.getContent())
                .maxCapacity(party.getMaxCapacity())
                .location(party.getLocation().isEmpty() ? "장소 미정" : party.getLocation())
                .partyDate(Optional.ofNullable(party.getPartyDate()).isEmpty() ? "날짜 미정" : party.getPartyDate().format(formatter))
                .partyImageUrl(party.getPartyImageUrl())
                .createdAt(party.getCreatedDate())
                .openMemberId(partyMaker.getId()).openMemberName(partyMaker.getNickname()).openMemberProfileImageUrl(partyMaker.getProfileImageUrl())
                .joinMemberInfo(List.of(joinMemberInfo))
                .build();
    }

    public static PartyTapResponseDto mapToPartyTapResponseDto(Party party, Member partyMaker, Integer joinMemberCount) {
        return PartyTapResponseDto.builder()
                .partyId(party.getId()).title(party.getTitle())
                .partyDate(Optional.ofNullable(party.getPartyDate()).isEmpty() ? "날짜 미정" : party.getPartyDate().format(formatter))
                .location(party.getLocation().isEmpty() ? "장소 미정" : party.getLocation())
                .openMemberName(partyMaker.getNickname()).openMemberProfileImageUrl(partyMaker.getProfileImageUrl())
                .joinMemberCount(joinMemberCount)
                .maxCapacity(party.getMaxCapacity()).partyImageUrl(party.getPartyImageUrl()).build();
    }

    public static PartyTapIncludingStateResponseDto mapPartyToPartyTapIncludingStateResponseDto(final Party party, final Member partyMaker, final Integer joinMemberCount) {
        State state = State.COMPLETED;

        if (party.getIsOpen() && party.getCardImageUrl() == null) {
            state = State.RECRUITING;
        } else if (!party.getIsOpen() && party.getCardImageUrl() == null) {
            state = State.IN_PROGRESS;
        } else if (!party.getIsOpen()){
            state = State.COMPLETED;
        }

        return PartyTapIncludingStateResponseDto.builder()
                .partyId(party.getId()).title(party.getTitle())
                .partyDate(Optional.ofNullable(party.getPartyDate()).isEmpty() ? "날짜 미정" : party.getPartyDate().format(formatter))
                .location(party.getLocation().isEmpty() ? "장소 미정" : party.getLocation())
                .openMemberName(partyMaker.getNickname()).openMemberProfileImageUrl(partyMaker.getProfileImageUrl())
                .joinMemberCount(joinMemberCount)
                .maxCapacity(party.getMaxCapacity()).partyImageUrl(party.getPartyImageUrl())
                .state(state).build();
    }

    public static PartyDetailInfoResponseDto mapToPartyDetailInfoResponseDto(Party party, Member member, Long channelMakerId, Long partyCommentCount, Comment lastComment) {
        Member partyMaker = party.getParticipationList().stream().filter(Participation::getIsMaker).map(Participation::getMember).findFirst().orElseThrow();
        List<Member> joiner = party.getParticipationList().stream().map(Participation::getMember).collect(Collectors.toList());
        List<PartyDetailInfoResponseDto.JoinMemberInfo> joinMemberInfoList = joiner.stream().map(joinMember -> PartyDetailInfoResponseDto.JoinMemberInfo.builder()
                .memberId(joinMember.getId())
                .isChannelMaker(joinMember.getId().equals(channelMakerId))
                .isMaker(joinMember.getId().equals(partyMaker.getId()))
                .memberName(joinMember.getNickname())
                .memberProfileImageUrl(joinMember.getProfileImageUrl())
                .build()).collect(Collectors.toList());

        PartyDetailInfoResponseDto.RoughCommentInfo commentInfo = PartyDetailInfoResponseDto.RoughCommentInfo.builder()
                .partyCommentCount(partyCommentCount)
                .commentContent(lastComment != null ? lastComment.getContent() : null)
                .commentWriterId(lastComment != null ? lastComment.getWriter().getId() : null)
                .commentWriterName(lastComment != null ? lastComment.getWriter().getNickname() : null)
                .commentWriterProfileImageUrl(lastComment != null ? lastComment.getWriter().getProfileImageUrl() : null).build();

        return PartyDetailInfoResponseDto.builder()
                .partyId(party.getId()).title(party.getTitle()).content(party.getContent()).partyImageUrl(party.getPartyImageUrl())
                .partyDate(Optional.ofNullable(party.getPartyDate()).isEmpty() ? "날짜 미정" : party.getPartyDate().format(formatter))
                .location(party.getLocation().isEmpty() ? "장소 미정" : party.getLocation())
                .createdAt(party.getCreatedDate())
                .openMemberId(partyMaker.getId()).openMemberName(partyMaker.getNickname()).openMemberProfileImageUrl(partyMaker.getProfileImageUrl())
                .roughCommentInfo(commentInfo)
                .maxCapacity(party.getMaxCapacity()).joinMemberInfo(joinMemberInfoList)
                .buttonState(determineButtonState(party, partyMaker, member, joiner))
                .build();
    }

    public static PartyDetailInfoResponseDto.ButtonState determineButtonState(Party party, Member partyMaker, Member member, List<Member> joiner) {
        if (party.getCardImageUrl() != null) { return PartyDetailInfoResponseDto.ButtonState.END; }

        boolean isMemberPartyMaker = partyMaker.getId().equals(member.getId());
        boolean isMemberJoiner = joiner.stream().anyMatch(joinMember -> joinMember.getId().equals(member.getId()));

        if (isMemberPartyMaker) {
            return party.getIsOpen() ? PartyDetailInfoResponseDto.ButtonState.CLOSE : PartyDetailInfoResponseDto.ButtonState.MAKE_CARD;
        }

        if (isMemberJoiner) {
            return party.getIsOpen() ? PartyDetailInfoResponseDto.ButtonState.JOIN_CANCEL : PartyDetailInfoResponseDto.ButtonState.IN_PROGRESS;
        }

        return PartyDetailInfoResponseDto.ButtonState.JOIN;
    }

    public static CompletePartyResponseDto mapToCompletePartyResponseDto(Party party) {
        return CompletePartyResponseDto.builder()
                .partyId(party.getId())
                .partyDate(party.getPartyDate())
                .title(party.getTitle())
                .content(party.getContent())
                .partyCardImageUrl(party.getCardImageUrl()).build();
    }

}
