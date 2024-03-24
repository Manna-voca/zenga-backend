package com.mannavoca.zenga.domain.praise.application.service;

import java.util.List;
import java.util.Optional;

import com.mannavoca.zenga.common.annotation.DistributedLock;
import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.mapper.PraiseMapper;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import com.mannavoca.zenga.domain.praise.domain.service.CandidateService;
import com.mannavoca.zenga.domain.praise.domain.service.MemberPraiseService;
import com.mannavoca.zenga.domain.praise.domain.service.PraiseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PraiseCreateUseCase {
    private final MemberService memberService;
    private final PraiseService praiseService;
    private final CandidateService candidateService;
    private final MemberPraiseService memberPraiseService;

    @DistributedLock(key = "'CreatingMemberPraise:' + #channelId + '-' + #memberId ")
    public CurrentTodoPraiseResponseDto getCurrentTodoPraiseAndMemberList(Long memberId, Long channelId) {
        if (memberService.countMemberByChannelId(channelId) < 10) {
            throw BusinessException.of(Error.NOT_ENOUGH_MEMBER);
        }
        Optional<MemberPraise> currentTodoPraiseOpt = memberPraiseService.findCurrentTodoPraiseForMember(memberId);

        if (currentTodoPraiseOpt.isPresent()) { // API 요청을 한적이 있어서 저장이 된 것이고 다시 조회하면 memberPraise 를 찾아서 데이터를 리턴
            if (currentTodoPraiseOpt.get().getPraisedMember() == null){
                List<Member> candidates = candidateService.getCandidates(memberId);
                return PraiseMapper.mapToCurrentTodoPraiseResponseDto(currentTodoPraiseOpt.get(), candidates);
            } else { // 칭찬한 대상이 있으면 null 리턴
                return null;
            }
        } else { // 완전 처음 조회해서 데이터도 저장하고 값을 리턴해야함
            Praise randomPraise = praiseService.findRandomPraise();
            List<Member> randomMembersByChannelId = memberService.findDynamicRandomMembersByChannelId(memberId, channelId);

            Member member = memberService.findMemberById(memberId);
            if (candidateService.existsCandidateHistory(memberId)) {
                candidateService.deletedAndSaveBulkCandidates(member, randomMembersByChannelId);
            } else {
                candidateService.saveCandidateBulk(member, randomMembersByChannelId);
            }

            MemberPraise newMemberPraise = memberPraiseService.createNewMemberPraise(member, randomPraise);
            return PraiseMapper.mapToCurrentTodoPraiseResponseDto(newMemberPraise, randomMembersByChannelId);
        }
    }
}
