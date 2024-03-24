package com.mannavoca.zenga.domain.ranking.domain.model;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "zg_ranking_point")
public class RankingPoint extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
    private String contents;
    private Integer point;

    private RankingPoint(Member member, String contents, Integer point) {
        this.contents = contents;
        this.member = member;
        this.point = point;
    }

    public static RankingPoint create(Member member, String contents, Integer point) {
        return new RankingPoint(member, contents, point);
    }
}
