package com.mannavoca.zenga.domain.comment.infrastructure;

import com.mannavoca.zenga.domain.comment.domain.entity.Comment;
import com.mannavoca.zenga.domain.comment.domain.repository.CommentRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.mannavoca.zenga.domain.comment.domain.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Comment> findAllParentCommentInParty(Long partyId, Long commentId, Pageable pageable) {
        List<Long> parentCommentIds = queryFactory
                .select(comment.id)
                .from(comment)
                .where(
                        comment.party.id.eq(partyId)
                                .and(comment.parent.isNull()),
                        gtCommentId(commentId)
                )
                .orderBy(comment.id.asc())
                .limit(pageable.getPageSize() + 1)
                .fetch();
        Slice<Long> checkedLastPage = checkLastPage(pageable, parentCommentIds);
        List<Comment> commentList = queryFactory
                .select(comment)
                .from(comment)
                .innerJoin(comment.writer).fetchJoin()
                .leftJoin(comment.parent).fetchJoin()
                .where(
                        comment.id.in(parentCommentIds)
                                .or(comment.parent.id.in(parentCommentIds))
                )
                .orderBy(comment.parent.id.asc().nullsFirst(), comment.createdDate.asc())
                .fetch();
        return new SliceImpl<>(commentList, pageable, checkedLastPage.hasNext());
    }

    @Override
    public List<Comment> findAllChildCommentInParty(Long parentId) {
        return queryFactory
                .select(comment)
                .from(comment)
                .where(
                        comment.parent.id.eq(parentId)
                                .and(comment.parent.isNotNull())
                )
                .orderBy(comment.id.asc())
                .fetch();
    }

    @Override
    public Long countPartyCommentAmount(Long partyId) {
        Long count = queryFactory
                .select(comment.id.count())
                .from(comment)
                .where(comment.party.id.eq(partyId))
                .fetchOne();
        return count == null ? 0L : count;
    }

    @Override
    public Optional<Comment> findLastCommentByPartyId(Long partyId) {
        JPAQuery<Comment> query = queryFactory.select(comment)
                .from(comment)
                .innerJoin(comment.writer)
                .fetchJoin()
                .where(comment.party.id.eq(partyId))
                .orderBy(comment.id.desc())
                .limit(1);

        return Optional.ofNullable(query.fetchOne());
    }


    private BooleanExpression gtCommentId(Long commentId) {
        return commentId != null ? comment.id.gt(commentId) : null;
    }


    private <T> Slice<T> checkLastPage(Pageable pageable, List<T> results) {
        boolean hasNext = false;
        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }
}
