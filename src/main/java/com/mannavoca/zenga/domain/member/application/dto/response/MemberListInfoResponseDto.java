package com.mannavoca.zenga.domain.member.application.dto.response;

import lombok.*;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberListInfoResponseDto {
    private Long count;
    private Slice<MemberInfoResponseDto> memberInfoResponseDtoSlice;
}
