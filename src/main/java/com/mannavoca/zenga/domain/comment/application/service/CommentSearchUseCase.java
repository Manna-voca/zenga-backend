package com.mannavoca.zenga.domain.comment.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.dto.SliceResponse;
import com.mannavoca.zenga.domain.comment.application.dto.response.CommentInfoResponseDto;
import com.mannavoca.zenga.domain.comment.application.mapper.CommentMapper;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.comment.domain.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@UseCase
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentSearchUseCase {
    private final CommentService commentService;

    public SliceResponse<CommentInfoResponseDto> getParentCommentList(Long partyId, Long commentId, Pageable pageable) {
        Slice<Comment> allParentCommentInParty = commentService.findAllParentCommentInParty(partyId, commentId, pageable);
        return CommentMapper.mapToParentCommentInfoResponse(allParentCommentInParty);
    }

    public List<CommentInfoResponseDto> getChildCommentList(Long parentId) {
        List<Comment> allChildCommentInParty = commentService.findAllChildCommentInParty(parentId);
        return CommentMapper.mapToChildCommentInfoResponse(allChildCommentInParty);
    }
}
