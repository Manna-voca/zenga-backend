package com.mannavoca.zenga.domain.comment.domain.repository;

import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CommentRepositoryCustom {
    Slice<Comment> findAllParentCommentInParty(Long partyId, Long commentId, Pageable pageable);
    List<Comment> findAllChildCommentInParty(Long parentId);
}
