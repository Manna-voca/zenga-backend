package com.mannavoca.zenga.domain.praise.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CurrentTodoPraiseResponseDto {
    private String praise;
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
    public CurrentTodoPraiseResponseDto(String praise, List<MemberInfoList> memberList) {
        this.praise = praise;
        this.memberList = memberList;
    }

}
