package com.mannavoca.zenga.domain.channel.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CreatingChannelRequestDto {
    private String name;
    private String logoImageUrl;
    private String description;
}
