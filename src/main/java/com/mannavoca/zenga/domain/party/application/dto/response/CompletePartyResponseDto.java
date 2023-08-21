package com.mannavoca.zenga.domain.party.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CompletePartyResponseDto {
    private Long partyId;
    private LocalDateTime partyDate;
    private String title;
    private String content;
    private String partyCardImageUrl;

    @Builder
    public CompletePartyResponseDto(Long partyId, LocalDateTime partyDate, String title, String content, String partyCardImageUrl) {
        this.partyId = partyId;
        this.partyDate = partyDate;
        this.title = title;
        this.content = content;
        this.partyCardImageUrl = partyCardImageUrl;
    }
}
