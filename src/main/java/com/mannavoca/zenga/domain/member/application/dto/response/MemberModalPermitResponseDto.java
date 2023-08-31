package com.mannavoca.zenga.domain.member.application.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberModalPermitResponseDto {
    private Long memberId;
    private Boolean praiseModal;
    private Boolean pointModal;
}
