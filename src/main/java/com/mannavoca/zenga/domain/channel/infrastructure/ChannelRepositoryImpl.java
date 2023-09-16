package com.mannavoca.zenga.domain.channel.infrastructure;

import com.mannavoca.zenga.domain.channel.application.dto.response.ChannelAndMemberIdResponseDto;
import com.mannavoca.zenga.domain.channel.domain.entity.Channel;
import com.mannavoca.zenga.domain.channel.domain.repository.ChannelRepositoryCustom;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mannavoca.zenga.domain.channel.domain.entity.QChannel.channel;
import static com.mannavoca.zenga.domain.member.domain.entity.QMember.member;

@RequiredArgsConstructor
@Repository
public class ChannelRepositoryImpl implements ChannelRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Channel> findAllChannelsByUserId(Long userId) {
        List<Channel> channelList =
                queryFactory
                        .select(channel)
                        .from(channel)
                        .innerJoin(channel.memberList, member)
                        .where(
                                member.user.id.eq(userId)
                        )
                        .orderBy(channel.id.desc())
                        .fetch();

        return channelList;
    }

    @Override
    public boolean existsByChannelId(Long channelId) {
        return queryFactory
                .from(channel)
                .where(
                        channel.id.eq(channelId)
                )
                .fetchFirst() != null;
    }

    @Override
    public List<ChannelAndMemberIdResponseDto> findAllChannelsIncludingMemberIdByUserId(Long userId) {
        return queryFactory
                .select(Projections.constructor(ChannelAndMemberIdResponseDto.class,
                        channel.id,
                        channel.name,
                        channel.logoImageUrl,
                        channel.code,
                        member.id
                ))
                .from(channel)
                .innerJoin(channel.memberList, member)
                .where(
                        member.user.id.eq(userId)
                )
                .orderBy(member.id.desc())
                .fetch();
    }
}
