package com.mannavoca.zenga.domain.praise.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CurrentTodoPraiseResponseDto {
    private Long memberPraiseId;
    private String praise;
    private int shuffleCount;
    private List<MemberInfoList> memberList;

    @Getter
    @Builder
    @AllArgsConstructor
    public static class MemberInfoList {
        private Long memberId;
        private String name;
        private String profileImageUrl;
    }

    @Builder
    public CurrentTodoPraiseResponseDto(Long memberPraiseId, String praise, int shuffleCount, List<MemberInfoList> memberList) {
        this.memberPraiseId = memberPraiseId;
        this.praise = praise;
        this.shuffleCount = shuffleCount;
        this.memberList = memberList;
    }

}
