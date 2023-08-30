package com.mannavoca.zenga.domain.party.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApplyPartyRequestDto {
    private Long channelId;
    private Long partyId;
}
