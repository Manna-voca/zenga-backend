package com.mannavoca.zenga.domain.ranking.domain.model;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zg_ranking_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RankingPoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private Integer point;

    private RankingPoint(Member member, Integer point) {
        this.member = member;
        this.point = point;
    }

    public static RankingPoint create(Member member, Integer point) {
        return new RankingPoint(member, point);
    }
}
