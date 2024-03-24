package com.mannavoca.zenga.domain.ranking.domain.repository;

import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;

import java.util.List;

public interface RankingPointCustomRepository {
    MemberRankDto findMemberRank(Long memberId);

    List<MemberRankDto> findChannelMemberRanks(Long channelId);
}
