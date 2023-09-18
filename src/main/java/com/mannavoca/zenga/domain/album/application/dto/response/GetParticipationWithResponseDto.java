package com.mannavoca.zenga.domain.album.application.dto.response;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetParticipationWithResponseDto {

    private List<ParticipationWithResponseDto> ParticipationList;

    public static GetParticipationWithResponseDto of(List<ParticipationWithResponseDto> participationList) {
        GetParticipationWithResponseDto getParticipationWithResponseDto = new GetParticipationWithResponseDto();
        getParticipationWithResponseDto.ParticipationList = participationList;

        return getParticipationWithResponseDto;
    }

    @Getter
    public static class ParticipationWithResponseDto {
        private Long id;

        private String profileUrl;

        private String nickname;

        public static ParticipationWithResponseDto of(Member member) {
            ParticipationWithResponseDto participationWithResponseDto = new ParticipationWithResponseDto();
            participationWithResponseDto.id = member.getId();
            participationWithResponseDto.profileUrl = member.getProfileImageUrl();
            participationWithResponseDto.nickname = member.getNickname();

            return participationWithResponseDto;
        }

    }
}