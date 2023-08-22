package com.mannavoca.zenga.domain.party.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartyDetailInfoResponseDto {
    private Long partyId;
    private String title;
    private String content;
    private String partyImageUrl;
    private String partyDate;
    private String location;

    private LocalDateTime createdAt;
    private String openMemberName;
    private String openMemberProfileImageUrl;

    private Integer maxCapacity;
    private List<JoinMemberInfo> joinMemberInfo;
    private ButtonState buttonState;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinMemberInfo {
        private Long memberId;
        private String memberName;
        private String memberProfileImageUrl;
    }

    public enum ButtonState {
        // 차례대로 모임 참가하기, 모임 참가 취소, 모임 진행 중, 모임 종료, 모집 마감, 모임 카드 만들기를 의미함
        JOIN, JOIN_CANCEL, IN_PROGRESS, END, CLOSE, MAKE_CARD
    }

    @Builder
    public PartyDetailInfoResponseDto(Long partyId, String title, String content, Integer maxCapacity, String location, String partyDate, String partyImageUrl, LocalDateTime createdAt, String openMemberName, String openMemberProfileImageUrl, List<JoinMemberInfo> joinMemberInfo, ButtonState buttonState) {
        this.partyId = partyId;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.location = location;
        this.partyDate = partyDate;
        this.partyImageUrl = partyImageUrl;
        this.createdAt = createdAt;
        this.openMemberName = openMemberName;
        this.openMemberProfileImageUrl = openMemberProfileImageUrl;
        this.joinMemberInfo = joinMemberInfo;
        this.buttonState = buttonState;
    }
}
