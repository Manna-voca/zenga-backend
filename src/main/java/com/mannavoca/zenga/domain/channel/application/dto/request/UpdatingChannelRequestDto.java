package com.mannavoca.zenga.domain.channel.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdatingChannelRequestDto {

    private String name;
    private String logoImageUrl;
}
