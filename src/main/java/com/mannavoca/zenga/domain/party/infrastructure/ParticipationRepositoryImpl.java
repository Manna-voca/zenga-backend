package com.mannavoca.zenga.domain.party.infrastructure;

import com.mannavoca.zenga.domain.party.domain.repository.ParticipationRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.mannavoca.zenga.domain.member.domain.entity.QMember.member;
import static com.mannavoca.zenga.domain.party.domain.entity.QParticipation.participation;
import static com.mannavoca.zenga.domain.party.domain.entity.QParty.party;

@RequiredArgsConstructor
@Repository
public class ParticipationRepositoryImpl implements ParticipationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long countFinishedParticipationByMemberId(final Long memberId) {
        return queryFactory.select(participation.countDistinct())
                .from(participation)
                .innerJoin(participation.party, party)
                .innerJoin(participation.member, member)
                .where(member.id.eq(memberId), party.cardImageUrl.isNotNull())
                .fetchOne();
    }
}
