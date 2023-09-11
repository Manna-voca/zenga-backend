package com.mannavoca.zenga.domain.channel.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChannelOwnershipInfoResponseDto {
    private Long id;
    private String name;
    private String logoImageUrl;
    private String code;
    private Boolean isOwner;

    public ChannelOwnershipInfoResponseDto(Long id, String name, String logoImageUrl, String code) {
        this.id = id;
        this.name = name;
        this.logoImageUrl = logoImageUrl;
        this.code = code;
    }
}
