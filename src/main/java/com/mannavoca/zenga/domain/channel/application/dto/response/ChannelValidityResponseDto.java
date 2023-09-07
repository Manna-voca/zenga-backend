package com.mannavoca.zenga.domain.channel.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChannelValidityResponseDto {
    private Boolean isValid;
    private Long memberCount;
}
