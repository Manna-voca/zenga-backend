package com.mannavoca.zenga.domain.praise.application.dto.event;

import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "of")
public class PraisedMemberEventDto {
    private Long praisedMemberId;
    private PraiseType praiseType;
}
