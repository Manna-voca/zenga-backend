package com.mannavoca.zenga.domain.member.infrastructure;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mannavoca.zenga.domain.member.domain.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findMembersByChannelId(Long memberId, Long channelId) {
        return queryFactory
                .selectFrom(member)
                .where(
                        member.channel.id.eq(channelId)
                                .and(member.id.ne(memberId))
                )
                .fetch();
    }

    @Override
    public Slice<Member> findAllMemberSlicesByChannelId(Long channelId, Long memberIdCursor, String keyword, Pageable pageable) {
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .where(member.channel.id.eq(channelId), gtMemberId(memberIdCursor), containsKeyword(keyword))
                .orderBy(member.level.asc(), member.nickname.asc(), member.id.asc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return checkLastPage(pageable, memberList);
    }


    @Override
    public boolean existsByMemberId(Long memberId) {
        return queryFactory
                .from(member)
                .where(
                        member.id.eq(memberId)
                )
                .fetchFirst() != null;
    }

    @Override
    public boolean existsByUserIdAndChannelId(Long userId, Long channelId) {
        return queryFactory
                .from(member)
                .where(
                        member.user.id.eq(userId)
                                .and(member.channel.id.eq(channelId))
                )
                .fetchFirst() != null;
    }

    @Override
    public Long countMemberByChannelId(Long channelId) {
        return queryFactory
                .select(member.count())
                .from(member)
                .where(
                        member.channel.id.eq(channelId)
                )
                .fetchFirst();
    }

    private Slice<Member> checkLastPage(Pageable pageable, List<Member> results) {
        boolean hasNext = false;
        // 조회한 결과 개수가 요청한 페이지 사이즈보다 크면 뒤에 더 있음, next = true
        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }
        return new SliceImpl<>(results, pageable, hasNext);
    }

    private BooleanExpression gtMemberId(Long memberIdCursor) {
        return memberIdCursor == null ? null : member.id.gt(memberIdCursor);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return keyword == null ? null : member.nickname.contains(keyword);
    }
}
