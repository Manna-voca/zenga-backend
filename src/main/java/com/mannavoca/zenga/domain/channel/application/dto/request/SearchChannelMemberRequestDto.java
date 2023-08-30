package com.mannavoca.zenga.domain.channel.application.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SearchChannelMemberRequestDto {
    private Long cursor;
    private String keyword;
}
