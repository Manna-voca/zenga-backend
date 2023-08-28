package com.mannavoca.zenga.domain.comment.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.annotation.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentWriteRequestDto {
    private Long channelId;
    private Long partyId;
    @Nullable
    private Long parentId;
    private String content;
}
