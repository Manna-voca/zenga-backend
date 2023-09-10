package com.mannavoca.zenga.domain.praise.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.common.dto.PageResponse;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.ReceivedPraiseInfoResponseDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.SendPraiseInfoResponseDto;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class PraiseMapper {

    public static CurrentTodoPraiseResponseDto mapToCurrentTodoPraiseResponseDto(MemberPraise currentTodoPraiseForMember, List<Member> candidates) {
        List<CurrentTodoPraiseResponseDto.MemberInfoList> memberInfoLists = candidates.stream()
                .map(member -> CurrentTodoPraiseResponseDto.MemberInfoList.builder()
                        .memberId(member.getId())
                        .name(member.getNickname())
                        .profileImageUrl(member.getProfileImageUrl())
                        .build()
                ).collect(Collectors.toList());
        return CurrentTodoPraiseResponseDto.builder()
                .memberPraiseId(currentTodoPraiseForMember.getId())
                .praise(currentTodoPraiseForMember.getPraise().getDescription())
                .shuffleCount(currentTodoPraiseForMember.getShuffleCount())
                .memberList(memberInfoLists)
                .build();
    }

    public static PageResponse<ReceivedPraiseInfoResponseDto> mapToReceivedPraiseInfoResponseDto(Page<MemberPraise> receivedPraiseList) {
        List<ReceivedPraiseInfoResponseDto> responseDtos = receivedPraiseList.getContent().stream()
                .map(memberPraise -> ReceivedPraiseInfoResponseDto.builder()
                        .memberPraiseId(memberPraise.getId())
                        .isOpened(memberPraise.getIsOpen())
                        .praiseDescription(memberPraise.getPraise().getDescription())
                        .praiseType(memberPraise.getPraise().getCategory())
                        .memberName(memberPraise.getIsOpen() ? memberPraise.getPraiseMember().getNickname() : "익명")
                        .memberProfileImageUrl(memberPraise.getIsOpen() ? memberPraise.getPraiseMember().getProfileImageUrl() : null)
                        .build()
                ).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, receivedPraiseList.getPageable(), receivedPraiseList.getTotalElements()));
    }

    public static PageResponse<SendPraiseInfoResponseDto> mapToSendPraiseInfoResponseDto(Page<MemberPraise> myCompletePraiseList) {
        List<SendPraiseInfoResponseDto> responseDtos = myCompletePraiseList.getContent().stream()
                .map(memberPraise -> SendPraiseInfoResponseDto.builder()
                        .memberPraiseId(memberPraise.getId())
                        .praiseDescription(memberPraise.getPraise().getDescription())
                        .praiseType(memberPraise.getPraise().getCategory())
                        .memberName(memberPraise.getPraisedMember().getNickname())
                        .memberProfileImageUrl(memberPraise.getPraisedMember().getProfileImageUrl())
                        .build()
                ).collect(Collectors.toList());
        return PageResponse.of(new PageImpl<>(responseDtos, myCompletePraiseList.getPageable(), myCompletePraiseList.getTotalElements()));
    }
}
