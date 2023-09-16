package com.mannavoca.zenga.domain.channel.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChannelMemberCountResponseDto {
    private Long count;
}
