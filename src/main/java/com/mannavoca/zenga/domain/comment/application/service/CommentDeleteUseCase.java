package com.mannavoca.zenga.domain.comment.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.comment.domain.service.CommentService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class CommentDeleteUseCase {
    private final UserUtils userUtils;
    private final CommentService commentService;

    public void deleteComment(Long channelId, Long commentId) {
        Member writer = userUtils.getMember(channelId);
        Comment comment = commentService.findById(commentId);
        if (!comment.getWriter().getId().equals(writer.getId())) {
            // 작성자만 삭제 가능
            throw BusinessException.of(Error.INTERNAL_SERVER_ERROR); // TODO: 에러코드 정의해야함
        }
        commentService.deleteComment(comment);
    }
}
