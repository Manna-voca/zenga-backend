package com.mannavoca.zenga.domain.party.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePartyRequestDto {
    private String title;
    private String content;
    private Integer maxCapacity;
    private String location;
    private LocalDateTime partyDate;
    private String partyImageUrl;
}
