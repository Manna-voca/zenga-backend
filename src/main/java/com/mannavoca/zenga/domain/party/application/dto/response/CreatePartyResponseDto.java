package com.mannavoca.zenga.domain.party.application.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CreatePartyResponseDto {
    private Long partyId;
    private String title;
    private String content;
    private Integer maxCapacity;
    private String location;
    private LocalDateTime partyDate;
    private String partyImageUrl;
    private LocalDateTime createdAt;
    private Long openMemberId;
    private String openMemberName;
    private String openMemberProfileImageUrl;
    private List<JoinMemberInfo> joinMemberInfo;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinMemberInfo {
        private Long memberId;
        private String memberName;
        private String memberProfileImageUrl;
    }

    @Builder
    public CreatePartyResponseDto(Long partyId, String title, String content, Integer maxCapacity, String location, LocalDateTime partyDate, String partyImageUrl, LocalDateTime createdAt, Long openMemberId, String openMemberName, String openMemberProfileImageUrl, List<JoinMemberInfo> joinMemberInfo) {
        this.partyId = partyId;
        this.title = title;
        this.content = content;
        this.maxCapacity = maxCapacity;
        this.location = location;
        this.partyDate = partyDate;
        this.partyImageUrl = partyImageUrl;
        this.createdAt = createdAt;
        this.openMemberId = openMemberId;
        this.openMemberName = openMemberName;
        this.openMemberProfileImageUrl = openMemberProfileImageUrl;
        this.joinMemberInfo = joinMemberInfo;
    }
}
