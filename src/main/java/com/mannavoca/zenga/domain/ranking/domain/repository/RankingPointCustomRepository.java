package com.mannavoca.zenga.domain.ranking.domain.repository;

import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;

public interface RankingPointCustomRepository {
    MemberRankDto findMemberRank(Long memberId);
}
