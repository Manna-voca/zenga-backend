package com.mannavoca.zenga.domain.praise.application.dto.response;

import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SendPraiseInfoResponseDto {
    private Long memberPraiseId;
    private String praiseDescription;
    private PraiseType praiseType;
    private Long memberId;
    private String memberName;
    private String memberProfileImageUrl;

    @Builder
    public SendPraiseInfoResponseDto(Long memberPraiseId, String praiseDescription, PraiseType praiseType, Long memberId, String memberName, String memberProfileImageUrl) {
        this.memberPraiseId = memberPraiseId;
        this.praiseDescription = praiseDescription;
        this.praiseType = praiseType;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberProfileImageUrl = memberProfileImageUrl;
    }
}
