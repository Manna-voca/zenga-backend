package com.mannavoca.zenga.domain.praise.application.service;

import com.mannavoca.zenga.common.annotation.UseCase;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.util.UserUtils;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.notification.domain.entity.Notification;
import com.mannavoca.zenga.domain.notification.domain.service.NotificationService;
import com.mannavoca.zenga.domain.point.application.service.PointPolicyUseCase;
import com.mannavoca.zenga.domain.praise.application.dto.request.ChooseMemberPraiseRequestDto;
import com.mannavoca.zenga.domain.praise.application.dto.request.OpenMemberPraiseRequestDto;
import com.mannavoca.zenga.domain.praise.application.dto.response.CurrentTodoPraiseResponseDto;
import com.mannavoca.zenga.domain.praise.application.mapper.PraiseMapper;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.service.CandidateService;
import com.mannavoca.zenga.domain.praise.domain.service.MemberPraiseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@UseCase
@Transactional
@RequiredArgsConstructor
public class PraiseUpdateUseCase {
    private final UserUtils userUtils;
    private final CandidateService candidateService;
    private final PointPolicyUseCase pointPolicyUseCase;
    private final MemberPraiseService memberPraiseService;
    private final MemberService memberService;
    private final NotificationService notificationService;

    public CurrentTodoPraiseResponseDto getAgainCurrentTodoPraiseAndMemberList(Long channelId) {
        Member member = userUtils.getMember(channelId);
        Optional<MemberPraise> currentTodoPraiseOpt = memberPraiseService.findCurrentTodoPraiseForMember(member.getId());

        if (currentTodoPraiseOpt.isPresent() && currentTodoPraiseOpt.get().getPraisedMember() == null) {
            if (currentTodoPraiseOpt.get().getShuffleCount() == 1) { throw BusinessException.of(Error.CANNOT_SHUFFLE); }
            List<Member> candidates = candidateService.getCandidates(member.getId());
            memberPraiseService.updatePlusShuffleCountMemberPraise(currentTodoPraiseOpt.get());
            return PraiseMapper.mapToCurrentTodoPraiseResponseDto(currentTodoPraiseOpt.get(), candidates);
        } else {
            return null;
        }
    }

    public void choosePraise(ChooseMemberPraiseRequestDto chooseMemberPraiseRequestDto) {
        Member praisedMember = memberService.findMemberById(chooseMemberPraiseRequestDto.getPraisedMemberId());
        MemberPraise memberPraise = memberPraiseService.findMemberPraiseById(chooseMemberPraiseRequestDto.getMemberPraiseId());
        if (memberPraise.getPraisedMember() != null) { throw BusinessException.of(Error.ALREADY_PRAISED); }
        // 본인이 본인을 칭찬할 수 있는 경우를 그 전에 리스트 줄 때 막긴하는데 여기서도 막아야하나...?
        pointPolicyUseCase.accumulatePointByPraise(userUtils.getUser(), memberPraise.getPraiseMember(), praisedMember.getChannel().getName());
        memberPraiseService.updatePraisedMemberToMemberPraise(memberPraise, praisedMember);

        notificationService.createPraiseNotification(praisedMember, memberPraise.getPraise());
    }

    public void openMemberPraise(OpenMemberPraiseRequestDto openMemberPraiseRequestDto) {
        Member member = userUtils.getMember(openMemberPraiseRequestDto.getChannelId());
        MemberPraise memberPraise = memberPraiseService.findMemberPraiseById(openMemberPraiseRequestDto.getMemberPraiseId());
        if (!Objects.equals(memberPraise.getPraisedMember().getId(), member.getId())) { throw BusinessException.of(Error.NOT_MEMBER_PRAISE_OWNER); }

        pointPolicyUseCase.usePoint(member, member.getChannel().getName());

        memberPraiseService.updateOpenMemberPraise(memberPraise);
    }
}
