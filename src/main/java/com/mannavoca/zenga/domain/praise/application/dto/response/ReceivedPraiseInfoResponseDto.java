package com.mannavoca.zenga.domain.praise.application.dto.response;

import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceivedPraiseInfoResponseDto {
    private Long memberPraiseId;
    private Boolean isOpened;
    private String praiseDescription;
    private PraiseType praiseType;
    private Long memberId;
    private String memberName;
    private String memberProfileImageUrl;

    @Builder
    public ReceivedPraiseInfoResponseDto(Long memberPraiseId, Boolean isOpened, String praiseDescription, PraiseType praiseType, Long memberId, String memberName, String memberProfileImageUrl) {
        this.memberPraiseId = memberPraiseId;
        this.isOpened = isOpened;
        this.praiseDescription = praiseDescription;
        this.praiseType = praiseType;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberProfileImageUrl = memberProfileImageUrl;
    }
}
