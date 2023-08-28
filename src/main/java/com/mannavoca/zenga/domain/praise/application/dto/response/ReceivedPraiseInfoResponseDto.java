package com.mannavoca.zenga.domain.praise.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceivedPraiseInfoResponseDto {
    private Long receivedPraiseId;
    private Boolean isOpened;
    private String praiseDescription;
    private String memberName;
    private String memberProfileImageUrl;

    @Builder
    public ReceivedPraiseInfoResponseDto(Long receivedPraiseId, Boolean isOpened, String praiseDescription, String memberName, String memberProfileImageUrl) {
        this.receivedPraiseId = receivedPraiseId;
        this.isOpened = isOpened;
        this.praiseDescription = praiseDescription;
        this.memberName = memberName;
        this.memberProfileImageUrl = memberProfileImageUrl;
    }
}
