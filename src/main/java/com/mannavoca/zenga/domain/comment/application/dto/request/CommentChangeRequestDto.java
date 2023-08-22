package com.mannavoca.zenga.domain.comment.application.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentChangeRequestDto {
    private Long commentId;
    private String content;
}
