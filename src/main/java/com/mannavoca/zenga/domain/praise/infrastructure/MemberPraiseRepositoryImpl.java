package com.mannavoca.zenga.domain.praise.infrastructure;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.TimeSectionType;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepositoryCustom;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.mannavoca.zenga.domain.praise.domain.entity.QMemberPraise.memberPraise;

@Repository
@RequiredArgsConstructor
public class MemberPraiseRepositoryImpl implements MemberPraiseRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<MemberPraise> findAllNotPraised(){
        LocalDate oneDayAgo = LocalDate.now().minusDays(1);
        return queryFactory
                .selectFrom(memberPraise)
                .from(memberPraise)
                .where(
                        memberPraise.createdDate.year().eq(oneDayAgo.getYear())
                                .and(memberPraise.createdDate.month().eq(oneDayAgo.getMonthValue()))
                                .and(memberPraise.createdDate.dayOfMonth().eq(oneDayAgo.getDayOfMonth()))
                                .and(memberPraise.praisedMember.isNull())
                )
                .fetch();
    }

    @Override
    public Optional<MemberPraise> findCurrentTodoPraise(Long memberId) {
        LocalDateTime now = LocalDateTime.now();
        TimeSectionType timeSection = TimeSectionType.from(now);

        return Optional.ofNullable(queryFactory
                .selectFrom(memberPraise)
                .from(memberPraise)
                .where(
                        memberPraise.praiseMember.id.eq(memberId)
                                .and(memberPraise.timeSection.eq(timeSection))
                                .and(memberPraise.createdDate.year().eq(now.getYear()))
                                .and(memberPraise.createdDate.month().eq(now.getMonthValue()))
                                .and(memberPraise.createdDate.dayOfMonth().eq(now.getDayOfMonth()))
                ).fetchOne());
    }

    @Override
    public Page<MemberPraise> getReceivedPraiseList(Long memberId, Pageable pageable) {
        List<MemberPraise> memberPraiseList = queryFactory
                .selectFrom(memberPraise)
                .where(
                        memberPraise.praisedMember.isNotNull()
                                .and(memberPraise.praisedMember.id.eq(memberId))
                )
                .orderBy(memberPraise.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> countQuery = queryFactory
                .select(memberPraise.count())
                .from(memberPraise)
                .where(
                        memberPraise.praisedMember.isNotNull()
                                .and(memberPraise.praisedMember.id.eq(memberId))
                );
        return PageableExecutionUtils.getPage(memberPraiseList, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<MemberPraise> getMyCompletePraiseList(Long memberId, Pageable pageable) {
        List<MemberPraise> memberPraiseList = queryFactory
                .selectFrom(memberPraise)
                .where(
                        memberPraise.praisedMember.isNotNull()
                                .and(memberPraise.praiseMember.id.eq(memberId))
                )
                .orderBy(memberPraise.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(memberPraise.count())
                .from(memberPraise)
                .where(
                        memberPraise.praisedMember.isNotNull()
                                .and(memberPraise.praiseMember.id.eq(memberId))
                );

        return PageableExecutionUtils.getPage(memberPraiseList, pageable, countQuery::fetchOne);
    }
}
