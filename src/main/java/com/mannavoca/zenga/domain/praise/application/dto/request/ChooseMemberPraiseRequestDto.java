package com.mannavoca.zenga.domain.praise.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChooseMemberPraiseRequestDto {
    private Long memberPraiseId;
    private Long praisedMemberId;
}
