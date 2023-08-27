package com.mannavoca.zenga.domain.comment.application.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfoResponseDto {
    private Long commentId;
    private Long parentId;
    private String content;
    private LocalDateTime createdAt;
    private String writerName;
    private String writerProfileImageUrl;

    @Builder
    public CommentInfoResponseDto(Long commentId, Long parentId, String content, LocalDateTime createdAt, String writerName, String writerProfileImageUrl) {
        this.commentId = commentId;
        this.parentId = parentId;
        this.content = content;
        this.createdAt = createdAt;
        this.writerName = writerName;
        this.writerProfileImageUrl = writerProfileImageUrl;
    }
}
