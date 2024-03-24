package com.mannavoca.zenga.domain.ranking.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.ranking.domain.model.RankingPoint;
import com.mannavoca.zenga.domain.ranking.domain.repository.RankingPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@DomainService
@Transactional
public class RankingPointPolicyService {
    private static final int RANKING_POINT_BY_PRAISE = 50;
    private static final int RANKING_POINT_BY_PARTY_FINISH = 100;
    private static final String RANKING_POINT_DESCRIPTION_BY_PRAISE = "[%s] 칭찬 포인트가 적립되었어요!";
    private static final String RANKING_POINT_DESCRIPTION_BY_PARTY_FINISH = "[%s] 모임에 참여하여 포인트가 적립되었어요!";

    private final RankingPointRepository rankingPointRepository;

    public void accumulateRankingPointByPraise(final Member member, final String channelName) {
        final RankingPoint rankingPoint = RankingPoint.create(member, String.format(RANKING_POINT_DESCRIPTION_BY_PRAISE, channelName), RANKING_POINT_BY_PRAISE);

        rankingPointRepository.save(rankingPoint);
    }

    public void accumulateRankingPointByPartyFinish(final Member member, final String channelName) {
        final RankingPoint rankingPoint = RankingPoint.create(member, String.format(RANKING_POINT_DESCRIPTION_BY_PARTY_FINISH, channelName), RANKING_POINT_BY_PARTY_FINISH);

        rankingPointRepository.save(rankingPoint);
    }
}
