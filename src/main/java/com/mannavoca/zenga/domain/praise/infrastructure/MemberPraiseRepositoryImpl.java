package com.mannavoca.zenga.domain.praise.infrastructure;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.TimeSectionType;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

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

    public MemberPraise findTodayTodoPraise(Long memberId, TimeSectionType timeSectionType){
        LocalDate today = LocalDate.now();
        return queryFactory
                .selectFrom(memberPraise)
                .from(memberPraise)
                .where(
                        memberPraise.createdDate.year().eq(today.getYear())
                                .and(memberPraise.createdDate.month().eq(today.getMonthValue()))
                                .and(memberPraise.createdDate.dayOfMonth().eq(today.getDayOfMonth()))
                                .and(memberPraise.praiseMember.id.eq(memberId))
//                                .and(memberPraise.time.eq(timeSectionType)
                )
                .fetchOne();
    }
}
