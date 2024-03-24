package com.mannavoca.zenga.domain.ranking.domain.model;

import com.mannavoca.zenga.common.infrastructure.domain.BaseEntity;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.user.domain.entity.User;
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
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;
    private Integer point;

    private RankingPoint(User user, Channel channel, Integer point) {
        this.user = user;
        this.channel = channel;
        this.point = point;
    }

    public static RankingPoint create(User user, Channel channel, Integer point) {
        return new RankingPoint(user, channel, point);
    }
}
