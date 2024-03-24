package com.mannavoca.zenga.domain.member.infrastructure;

import static com.mannavoca.zenga.domain.channel.domain.entity.QChannel.*;
import static com.mannavoca.zenga.domain.member.domain.entity.QMember.*;
import static com.mannavoca.zenga.domain.user.domain.entity.QUser.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.entity.enumType.LevelType;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepositoryCustom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

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
    public Slice<Member> findAllMemberSlicesByChannelId(Long channelId, Long cursorId, String cursorName, String keyword, Pageable pageable) {
        List<Member> memberList = queryFactory
                .selectFrom(member)
                .where(member.channel.id.eq(channelId), containsKeyword(keyword), customCursor(cursorId, cursorName))
                .orderBy(member.nickname.asc(), member.id.asc())
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
                        member.channel.id.eq(channelId),
                        member.level.ne(LevelType.ADMIN)
                )
                .fetchFirst();
    }

    @Override
    public boolean isUserInChannelByMemberId(Long userId, Long memberId) {
        Long fetchedChannelId = queryFactory
                .select(channel.id)
                .from(member)
                .innerJoin(member.channel, channel)
                .where(member.id.eq(memberId))
                .fetchOne();

        return queryFactory
                .from(member)
                .innerJoin(member.user, user)
                .innerJoin(member.channel, channel)
                .where(user.id.eq(userId), channel.id.eq(fetchedChannelId))
                .fetchFirst() != null;
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

    private BooleanExpression gtMemberId(Long cursorId) {
        return cursorId == null ? null : member.id.gt(cursorId);
    }

    private BooleanExpression containsKeyword(String keyword) {
        return keyword == null ? null : member.nickname.contains(keyword);
    }

    private BooleanExpression gtMemberName(String cursorName) {
        return cursorName == null ? null : member.nickname.gt(cursorName);
    }

    private BooleanExpression customCursor(final Long cursorId, final String cursorName) {
        if (cursorId == null || cursorName == null) {
            return null;
        }

        return member.nickname.gt(cursorName)
                .or(member.nickname.eq(cursorName).and(member.id.gt(cursorId)));
    }
}
