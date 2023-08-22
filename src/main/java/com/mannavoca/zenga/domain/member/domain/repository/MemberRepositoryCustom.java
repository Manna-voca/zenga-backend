package com.mannavoca.zenga.domain.member.domain.repository;

import com.mannavoca.zenga.domain.member.domain.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMembersByChannelId(Long memberId, Long channelId);

    List<Member> findAllMembersByChannelId(Long channelId);
}
