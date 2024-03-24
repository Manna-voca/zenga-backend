package com.mannavoca.zenga.domain.ranking.infrastructure;

import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;
import com.mannavoca.zenga.domain.ranking.domain.repository.RankingPointCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RankingPointRepositoryImpl implements RankingPointCustomRepository {
    private static final String GET_MEMBER_RANK_QUERY =
            "SELECT\n" +
                    "    member_rank,\n" +
                    "    member_id,\n" +
                    "    profile_image_url,\n" +
                    "    nickname,\n" +
                    "    point\n" +
                    "FROM (\n" +
                    "    SELECT\n" +
                    "        DENSE_RANK() OVER (ORDER BY COALESCE(SUM(r.point), 0) DESC, m.nickname) AS member_rank,\n" +
                    "        m.id AS member_id,\n" +
                    "        m.profile_image_url AS profile_image_url,\n" +
                    "        m.nickname AS nickname,\n" +
                    "        COALESCE(SUM(r.point), 0) AS point \n" +
                    "    FROM\n" +
                    "        zg_member m\n" +
                    "    LEFT JOIN\n" +
                    "        zg_ranking_point r ON m.id = r.member_id\n" +
                    "    WHERE\n" +
                    "        m.id = ?\n" +
                    "    GROUP BY\n" +
                    "        m.id, m.profile_image_url, m.nickname\n" +
                    ") AS rankedMembers\n" +
                    "ORDER BY\n" +
                    "    member_rank, nickname"; // 이 부분이 수정되었습니다.



    private static final String GET_CHANNEL_MEMBER_RANKS_QUERY =
            "SELECT\n" +
                    "    member_rank,\n" +
                    "    member_id,\n" +
                    "    profile_image_url,\n" +
                    "    nickname,\n" +
                    "    point\n" +
                    "FROM (\n" +
                    "    SELECT\n" +
                    "        DENSE_RANK() OVER (ORDER BY COALESCE(SUM(r.point), 0) DESC, m.nickname) AS member_rank,\n" +
                    "        m.id AS member_id,\n" +
                    "        m.profile_image_url AS profile_image_url,\n" +
                    "        m.nickname AS nickname,\n" +
                    "        COALESCE(SUM(r.point), 0) AS point\n" +
                    "    FROM\n" +
                    "        zg_member m\n" +
                    "    LEFT JOIN\n" +
                    "        zg_ranking_point r ON m.id = r.member_id\n" +
                    "    INNER JOIN\n" +
                    "        zg_channel c ON m.channel_id = c.id\n" +
                    "    WHERE\n" +
                    "        c.id = ?\n" +
                    "    GROUP BY\n" +
                    "        m.id, m.profile_image_url, m.nickname\n" +
                    ") AS rankedMembers\n" +
                    "WHERE\n" +
                    "    point > 0\n" +
                    "ORDER BY\n" +
                    "    member_rank, nickname\n" +
                    "LIMIT\n" +
                    "    20";


    private final JdbcTemplate jdbcTemplate;

    @Override
    public MemberRankDto findMemberRank(final Long memberId) {
        return jdbcTemplate.queryForObject(GET_MEMBER_RANK_QUERY, (rs, rowNum) -> MemberRankDto.create(
                rs.getLong("member_id"),
                rs.getLong("member_rank"),
                rs.getString("profile_image_url"),
                rs.getString("nickname"),
                rs.getInt("point")
        ), memberId);
    }

    @Override
    public List<MemberRankDto> findChannelMemberRanks(Long channelId) {
        return jdbcTemplate.query(GET_CHANNEL_MEMBER_RANKS_QUERY, (rs, rowNum) -> MemberRankDto.create(
                rs.getLong("member_id"),
                rs.getLong("member_rank"),
                rs.getString("profile_image_url"),
                rs.getString("nickname"),
                rs.getInt("point")
        ), channelId);
    }
}
