package com.mannavoca.zenga.domain.party.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyTapResponseDto {
    private Long partyId;
    private String title;
    private String partyDate;
    private String location;
    private String openMemberName;
    private String openMemberProfileImageUrl;
    private Integer joinMemberCount;
    private Integer maxCapacity;
    private String partyImageUrl;

    @Builder
    public PartyTapResponseDto(Long partyId, String title, String partyDate, String location, String openMemberName, String openMemberProfileImageUrl, Integer joinMemberCount, Integer maxCapacity, String partyImageUrl) {
        this.partyId = partyId;
        this.title = title;
        this.partyDate = partyDate;
        this.location = location;
        this.openMemberName = openMemberName;
        this.openMemberProfileImageUrl = openMemberProfileImageUrl;
        this.joinMemberCount = joinMemberCount;
        this.maxCapacity = maxCapacity;
        this.partyImageUrl = partyImageUrl;
    }
}
