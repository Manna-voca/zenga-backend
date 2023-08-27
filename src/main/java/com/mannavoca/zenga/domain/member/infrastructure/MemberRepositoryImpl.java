package com.mannavoca.zenga.domain.member.infrastructure;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mannavoca.zenga.domain.member.domain.entity.QMember.member;
import static com.mannavoca.zenga.domain.channel.domain.entity.QChannel.channel;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findMembersByChannelId(Long memberId, Long channelId) {
        return queryFactory
                .selectFrom(member)
                .where(
                        member.channel.id.eq(channelId)
                                .and(member.id.ne(memberId))
                )
                .fetch();
    }

    @Override
    public List<Member> findAllMembersByChannelId(Long channelId) {
        return queryFactory
                .select(member)
                .from(member)
                .innerJoin(member.channel, channel)
                .where(
                        channel.id.eq(channelId)
                )
                .fetch();
    }


}
