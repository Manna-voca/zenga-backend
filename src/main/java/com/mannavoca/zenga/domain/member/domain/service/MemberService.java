package com.mannavoca.zenga.domain.member.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Member findMemberByUserId(Long userId, Long channelId) {
        return memberRepository.findMemberByUser_IdAndClub_Id(userId, channelId)
                .orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }
}
