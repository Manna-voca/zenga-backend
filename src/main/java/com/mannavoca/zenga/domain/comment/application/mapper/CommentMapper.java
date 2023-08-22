package com.mannavoca.zenga.domain.comment.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.comment.application.dto.response.CommentInfoResponseDto;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class CommentMapper {

    public static SliceResponse<CommentInfoResponseDto> mapToParentCommentInfoResponse(Slice<Comment> allParentCommentInParty) {
        Slice<CommentInfoResponseDto> responseDtos = allParentCommentInParty.map(
                comment -> CommentInfoResponseDto.builder()
                        .commentId(comment.getId())
                        .parentId(null)
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedDate())
                        .writerName(comment.getWriter().getNickname())
                        .writerProfileImageUrl(comment.getWriter().getProfileImageUrl())
                        .build());
        return SliceResponse.of(responseDtos);
    }

    public static List<CommentInfoResponseDto> mapToChildCommentInfoResponse(List<Comment> childCommentList) {
        return childCommentList.stream().map(
                comment -> CommentInfoResponseDto.builder()
                        .commentId(comment.getId())
                        .parentId(comment.getParent().getId())
                        .content(comment.getContent())
                        .createdAt(comment.getCreatedDate())
                        .writerName(comment.getWriter().getNickname())
                        .writerProfileImageUrl(comment.getWriter().getProfileImageUrl())
                        .build()).collect(Collectors.toList());
    }
}
