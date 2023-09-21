package com.mannavoca.zenga.domain.member.domain.repository;

import com.mannavoca.zenga.domain.member.domain.entity.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMembersByChannelId(Long memberId, Long channelId);

    Slice<Member> findAllMemberSlicesByChannelId(Long channelId, Long cursorId, String cursorName, String keyword, Pageable pageable);

    boolean existsByMemberId(Long memberId);
    boolean existsByUserIdAndChannelId(Long userId, Long channelId);

    Long countMemberByChannelId(Long channelId);

    boolean isUserInChannelByMemberId(Long userId, Long memberId);
}
