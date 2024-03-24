package com.mannavoca.zenga.domain.ranking.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRankDto {
    private Long memberId;
    private Long rank;
    private String userProfileImage;
    private String nickname;
    private Integer point;

    public static MemberRankDto create(Long userId, Long rank, String userProfileImage, String nickname, Integer point) {
        return new MemberRankDto(userId, rank, userProfileImage, nickname, point);
    }
}
