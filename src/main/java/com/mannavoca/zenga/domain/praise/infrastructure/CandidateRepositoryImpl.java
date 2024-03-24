package com.mannavoca.zenga.domain.praise.infrastructure;

import static com.mannavoca.zenga.domain.praise.domain.entity.QCandidate.*;

import org.springframework.stereotype.Repository;

import com.mannavoca.zenga.domain.praise.domain.repository.CandidateRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public void bulkDeleteCandidate(Long memberId) {
        queryFactory.delete(candidate1)
            .where(candidate1.member.id.eq(memberId))
            .execute();
    }
}
