package com.mannavoca.zenga.domain.ranking.application.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRankingPointHistoryDto {
    private List<RankingPointHistoryDto> rankingPointHistoryDtos;

    public static MemberRankingPointHistoryDto createFromRankingPointHistoryDtos(List<RankingPointHistoryDto> rankingPointHistoryDtos) {
        return new MemberRankingPointHistoryDto(rankingPointHistoryDtos);
    }
}
