package com.mannavoca.zenga.domain.member.application.dto.response;

import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class MemberInfoResponseDto {
    private Long id;
    private String profileImageUrl;
    private String name;
    private String introduction;
    private LevelType level;
}
