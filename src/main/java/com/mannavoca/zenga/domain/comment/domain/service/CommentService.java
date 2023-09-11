package com.mannavoca.zenga.domain.comment.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.comment.application.dto.request.CommentWriteRequestDto;
import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.comment.domain.repository.CommentRepository;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.party.domain.entity.Party;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Slice<Comment> findAllParentCommentInParty(Long partyId, Long commentId, Pageable pageable) {
        return commentRepository.findAllParentCommentInParty(partyId, commentId, pageable);
    }

    public List<Comment> findAllChildCommentInParty(Long parentId) {
        return commentRepository.findAllChildCommentInParty(parentId);
    }

    public void createComment(Member writer, Party party, CommentWriteRequestDto commentWriteRequestDto) {
        Comment comment = Comment.toEntity(party, writer, commentWriteRequestDto.getContent());
        if (commentWriteRequestDto.getParentId() != null) {
            Comment parent = commentRepository.findById(commentWriteRequestDto.getParentId())
                    .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
            comment.updateParent(parent);
        }
        commentRepository.save(comment);
    }

    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND)); // TODO: 에러코드 정의해야함
    }

    public void updateCommentContent(Comment comment, String content) {
        comment.updateContent(content);
        commentRepository.save(comment);
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public Long partyCommentCount(Long partyId) {
        return commentRepository.countPartyCommentAmount(partyId);
    }

    public Comment findLastCommentInParty(Long partyId) {
        return commentRepository.findLastCommentByPartyId(partyId)
                .orElse(null);
    }
}
