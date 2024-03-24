package com.mannavoca.zenga.domain.ranking.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.domain.ranking.application.dto.ChannelMemberRankDto;
import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;
import com.mannavoca.zenga.domain.ranking.domain.service.RankingQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@UseCase
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RankingPointReadUseCase {
    private final RankingQueryService rankingQueryService;

    public MemberRankDto findMyRank(final Long channelId) {
        return rankingQueryService.findMyRank(channelId);
    }

    public ChannelMemberRankDto findChannelMemberRanks(final Long channelId) {
        return rankingQueryService.findChannelMemberRanks(channelId);
    }
}
