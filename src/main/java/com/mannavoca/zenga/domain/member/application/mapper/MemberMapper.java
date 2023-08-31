package com.mannavoca.zenga.domain.member.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.member.application.dto.response.MemberInfoResponseDto;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class MemberMapper {
    public static MemberInfoResponseDto mapMemberToMemberInfoResponseDto(Member member) {
        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .profileImageUrl(member.getProfileImageUrl())
                .name(member.getNickname())
                .introduction(member.getIntroduction())
                .level(member.getLevel())
                .build();
    }

    public static Slice<MemberInfoResponseDto> MapMemberSliceToMemberInfoResponseDtoList(Slice<Member> memberSlice) {
        return memberSlice.map(MemberMapper::mapMemberToMemberInfoResponseDto);
    }

    public static List<MemberInfoResponseDto> mapMemberListToMemberInfoResponseDtoList(List<Member> memberList) {
        return memberList.stream().map(MemberMapper::mapMemberToMemberInfoResponseDto).collect(Collectors.toList());
    }
}
