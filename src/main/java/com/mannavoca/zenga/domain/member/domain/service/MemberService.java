package com.mannavoca.zenga.domain.member.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberByUserId(Long userId, Long channelId) {
        return memberRepository.findMemberByUser_IdAndClub_Id(userId, channelId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }

    public List<Member> find4RandomMembersByChannelId(Long memberId, Long channelId) {
        List<Member> membersByClubId = memberRepository.findMembersByClubId(memberId, channelId);

        Collections.shuffle(membersByClubId);  // 리스트를 랜덤하게 섞는다.
        // 만약 리스트의 크기가 4 보다 작다면, 리스트의 모든 멤버를 반환한다.
        return membersByClubId.subList(0, Math.min(membersByClubId.size(), 4));
    }
}
