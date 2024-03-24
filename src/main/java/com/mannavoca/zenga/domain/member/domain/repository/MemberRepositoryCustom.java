package com.mannavoca.zenga.domain.member.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.mannavoca.zenga.domain.member.domain.entity.Member;

public interface MemberRepositoryCustom {
    List<Member> findRandomMembersByChannelId(Long memberId, Long channelId, int limit);

    Slice<Member> findAllMemberSlicesByChannelId(Long channelId, Long cursorId, String cursorName, String keyword, Pageable pageable);

    boolean existsByMemberId(Long memberId);
    boolean existsByUserIdAndChannelId(Long userId, Long channelId);

    Long countMemberByChannelId(Long channelId);

    boolean isUserInChannelByMemberId(Long userId, Long memberId);
}
