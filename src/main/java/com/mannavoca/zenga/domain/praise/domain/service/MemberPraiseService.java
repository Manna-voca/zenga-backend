package com.mannavoca.zenga.domain.praise.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.TimeSectionType;
import com.mannavoca.zenga.domain.praise.domain.repository.MemberPraiseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@DomainService
@RequiredArgsConstructor
public class MemberPraiseService {
    private final MemberPraiseRepository memberPraiseRepository;

    public Optional<MemberPraise> findCurrentTodoPraiseForMember(Long memberId){
        return memberPraiseRepository.findCurrentTodoPraise(memberId);
    }

    public MemberPraise createNewMemberPraise(Member praiseMember, Praise praise) {
        return memberPraiseRepository.save(
                MemberPraise.builder()
                        .timeSection(TimeSectionType.from(LocalDateTime.now()))
                        .praiseMember(praiseMember).praisedMember(null)
                        .praise(praise).build()
        );
    }

    public void updatePlusShuffleCountMemberPraise(MemberPraise memberPraise) {
        memberPraise.updateShuffleCount();
        memberPraiseRepository.save(memberPraise);
    }

    public Page<MemberPraise> findReceivedPraiseList(Long memberId, int page) {
        Pageable pageable = PageRequest.of(page-1, 20);
        return memberPraiseRepository.getReceivedPraiseList(memberId, pageable);
    }

    public Page<MemberPraise> findMyCompletePraiseList(Long memberId, int page) {
        Pageable pageable = PageRequest.of(page-1, 20);
        return memberPraiseRepository.getMyCompletePraiseList(memberId, pageable);
    }

    public MemberPraise findMemberPraiseById(Long memberPraiseId) {
        return memberPraiseRepository.findById(memberPraiseId).orElseThrow(() -> BusinessException.of(Error.DATA_NOT_FOUND));
    }

    public void updatePraisedMemberToMemberPraise(MemberPraise memberPraise, Member praisedMember) {
        memberPraise.updatePraisedMember(praisedMember);
        memberPraiseRepository.save(memberPraise);
    }

    public void updateOpenMemberPraise(MemberPraise memberPraise) {
        memberPraise.updateOpen();
        memberPraiseRepository.save(memberPraise);
    }

    public Long getFinishedMemberPraiseCountByPraiseId(final Long praiseId) {
        return memberPraiseRepository.countFinishedPraiseByMemberId(praiseId);
    }

    public Boolean existsReceivedPraiseByMemberId(final Long memberId) {
        return memberPraiseRepository.existsReceivedPraiseByMemberId(memberId);
    }
}
