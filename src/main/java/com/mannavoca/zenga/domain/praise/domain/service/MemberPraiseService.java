package com.mannavoca.zenga.domain.praise.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberPraiseService {
    private final MemberPraiseRepository memberPraiseRepository;

    public Optional<MemberPraise> findTodayTodoPraiseForMember(Long memberId){
        return memberPraiseRepository.findTodayTodoPraise(memberId);
    }

    public void updatePlusShuffleCountMemberPraise(MemberPraise memberPraise) {
        memberPraise.updateShuffleCount();
        memberPraiseRepository.save(memberPraise);
    }
}
