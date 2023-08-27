package com.mannavoca.zenga.domain.comment.domain.repository;

import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
