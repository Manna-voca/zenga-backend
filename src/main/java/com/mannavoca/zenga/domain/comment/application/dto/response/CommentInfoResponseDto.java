package com.mannavoca.zenga.domain.comment.application.dto.response;

import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentInfoResponseDto {
    private Long commentId;
    private Long parentId;
    private Boolean isPartyMaker;
    private String content;
    private LocalDateTime createdAt;
    private Long writerId;
    private String writerName;
    private String writerProfileImageUrl;
    private List<CommentInfoResponseDto> childComments;

    public static CommentInfoResponseDto of(Comment comment, Long partyMakerId) {
        return CommentInfoResponseDto.builder()
                .commentId(comment.getId())
                .parentId(comment.getParent() == null ? null : comment.getParent().getId())
                .isPartyMaker(comment.getWriter().getId().equals(partyMakerId))
                .content(comment.getContent())
                .createdAt(comment.getCreatedDate())
                .writerId(comment.getWriter().getId())
                .writerName(comment.getWriter().getNickname())
                .writerProfileImageUrl(comment.getWriter().getProfileImageUrl())
                .childComments(new ArrayList<>())
                .build();
    }
}
