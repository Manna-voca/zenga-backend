package com.mannavoca.zenga.domain.member.application.dto.response;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
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
    private Long userId;
    private Long channelId;

    public static MemberInfoResponseDto of(Member member) {
        return MemberInfoResponseDto.builder()
                .id(member.getId())
                .profileImageUrl(member.getProfileImageUrl())
                .name(member.getUser().getName())
                .introduction(member.getIntroduction())
                .level(member.getLevel())
                .userId(member.getUser().getId())
                .channelId(member.getChannel().getId())
                .build();
    }
}
