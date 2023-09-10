package com.mannavoca.zenga.domain.channel.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChannelAndMemberIdResponseDto {
    private Long id;
    private String name;
    private String logoImageUrl;
    private String code;
    private Long memberId;
}
