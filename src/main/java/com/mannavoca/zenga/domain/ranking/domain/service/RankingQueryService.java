package com.mannavoca.zenga.domain.ranking.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.ranking.application.dto.ChannelMemberRankDto;
import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;
import com.mannavoca.zenga.domain.ranking.domain.repository.RankingPointRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@DomainService
@Transactional(readOnly = true)
public class RankingQueryService {
    private final RankingPointRepository rankingPointRepository;
    private final UserUtils userUtils;

    public MemberRankDto findMyRank(final Long channelId) {
        final Member member = userUtils.getMember(channelId);

        return rankingPointRepository.findMemberRank(member.getId());
    }

    public ChannelMemberRankDto findChannelMemberRanks(final Long channelId) {
        return ChannelMemberRankDto.create(rankingPointRepository.findChannelMemberRanks(channelId));
    }
}
