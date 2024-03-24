package com.mannavoca.zenga.domain.ranking.infrastructure;

import com.mannavoca.zenga.domain.ranking.application.dto.RankingPointHistoryDto;
import com.mannavoca.zenga.domain.ranking.domain.repository.RankingPointQuerydslRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mannavoca.zenga.domain.ranking.domain.model.QRankingPoint.rankingPoint;

@RequiredArgsConstructor
@Repository
public class RankingPointQuerydslRepositoryImpl implements RankingPointQuerydslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<RankingPointHistoryDto> findRankingPointHistories(Long memberId) {
        return jpaQueryFactory
                .select(Projections.constructor(
                                RankingPointHistoryDto.class,
                                rankingPoint.createdDate,
                                rankingPoint.contents,
                                rankingPoint.point
                        )
                ).from(rankingPoint)
                .where(rankingPoint.member.id.eq(memberId))
                .orderBy(rankingPoint.id.desc())
                .fetch();
    }
}
