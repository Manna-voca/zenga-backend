package com.mannavoca.zenga.domain.party.application.dto.response;

import com.mannavoca.zenga.domain.member.domain.entity.enumType.State;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyTapIncludingStateResponseDto {
    private Long partyId;
    private String title;
    private String partyDate;
    private String location;
    private String openMemberName;
    private String openMemberProfileImageUrl;
    private Integer joinMemberCount;
    private Integer maxCapacity;
    private String partyImageUrl;
    @Enumerated(javax.persistence.EnumType.STRING)
    private State state;

    @Builder
    public PartyTapIncludingStateResponseDto(Long partyId, String title, String partyDate, String location, String openMemberName, String openMemberProfileImageUrl, Integer joinMemberCount, Integer maxCapacity, String partyImageUrl, State state) {
        this.partyId = partyId;
        this.title = title;
        this.partyDate = partyDate;
        this.location = location;
        this.openMemberName = openMemberName;
        this.openMemberProfileImageUrl = openMemberProfileImageUrl;
        this.joinMemberCount = joinMemberCount;
        this.maxCapacity = maxCapacity;
        this.partyImageUrl = partyImageUrl;
        this.state = state;
    }
}
