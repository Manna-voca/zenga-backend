package com.mannavoca.zenga.domain.praise.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendPraiseInfoResponseDto {
    private Long donePraiseId;
    private String praiseDescription;
    private String memberName;
    private String memberProfileImageUrl;

    @Builder
    public SendPraiseInfoResponseDto(Long donePraiseId, String praiseDescription, String memberName, String memberProfileImageUrl) {
        this.donePraiseId = donePraiseId;
        this.praiseDescription = praiseDescription;
        this.memberName = memberName;
        this.memberProfileImageUrl = memberProfileImageUrl;
    }
}
