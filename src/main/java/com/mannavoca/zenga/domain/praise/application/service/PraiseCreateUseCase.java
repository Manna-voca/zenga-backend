package com.mannavoca.zenga.domain.praise.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.util.UserUtils;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@UseCase
@RequiredArgsConstructor
public class PraiseCreateUseCase {
    private final UserUtils userUtils;
    private final PraiseService praiseService;
    private final CandidateService candidateService;
    private final MemberPraiseService memberPraiseService;
    private final MemberService memberService;

    @Transactional
    public CurrentTodoPraiseResponseDto getCurrentTodoPraiseAndMemberList(Long channelId) {
        Member member = userUtils.getMember(channelId);
        Optional<MemberPraise> currentTodoPraiseOpt = memberPraiseService.findCurrentTodoPraiseForMember(member.getId());

        if (currentTodoPraiseOpt.isPresent()) { // API 요청을 한적이 있어서 저장이 된 것이고 다시 조회하면 memberPraise 를 찾아서 데이터를 리턴
            if (currentTodoPraiseOpt.get().getPraisedMember() == null){
                List<Member> candidates = candidateService.getCandidates(member.getId());
                return PraiseMapper.mapToCurrentTodoPraiseResponseDto(currentTodoPraiseOpt.get(), candidates);
            } else { // 칭찬한 대상이 있으면 null 리턴
                return null;
            }
        } else { // 완전 처음 조회해서 데이터도 저장하고 값을 리턴해야함
            Praise randomPraise = praiseService.findRandomPraise();
            List<Member> randomMembersByChannelId = memberService.find8RandomMembersByChannelId(member.getId(), channelId);
            if (candidateService.existsCandidateHistory(member.getId())) {
                candidateService.updateCandidates(member.getId(), randomMembersByChannelId);
            } else {
                candidateService.saveCandidateBulk(member, randomMembersByChannelId);
            }
            MemberPraise newMemberPraise = memberPraiseService.createNewMemberPraise(member, randomPraise);
            return PraiseMapper.mapToCurrentTodoPraiseResponseDto(newMemberPraise, randomMembersByChannelId);
        }
    }
}
