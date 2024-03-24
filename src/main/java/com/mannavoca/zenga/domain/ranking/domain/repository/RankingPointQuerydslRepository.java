package com.mannavoca.zenga.domain.ranking.domain.repository;

import com.mannavoca.zenga.domain.ranking.application.dto.RankingPointHistoryDto;

import java.util.List;

public interface RankingPointQuerydslRepository {
    List<RankingPointHistoryDto> findRankingPointHistories(Long memberId);
}
