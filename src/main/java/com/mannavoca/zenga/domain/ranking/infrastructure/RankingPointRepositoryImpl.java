package com.mannavoca.zenga.domain.ranking.infrastructure;

import com.mannavoca.zenga.domain.ranking.application.dto.MemberRankDto;
import com.mannavoca.zenga.domain.ranking.domain.repository.RankingPointCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RankingPointRepositoryImpl implements RankingPointCustomRepository {
    private static final String GET_MEMBER_RANK_QUERY =
            "SELECT\n" +
                    "    DENSE_RANK() OVER (ORDER BY point DESC, m.nickname) AS member_rank,\n" +
                    "    m.id AS member_id,\n" +
                    "    m.profile_image_url AS profile_image_url,\n" +
                    "    m.nickname AS nickname,\n" +
                    "    COALESCE(SUM(r.point), 0) AS point \n" +
                    "FROM\n" +
                    "    zg_member m\n" +
                    "LEFT JOIN\n" +
                    "    zg_ranking_point r ON m.id = r.member_id\n" +
                    "WHERE\n" +
                    "    m.id = ?\n" +
                    "GROUP BY\n" +
                    "    m.id, m.profile_image_url, m.nickname";

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
}
