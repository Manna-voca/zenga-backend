package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockPartyMapping;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.service.BlockService;
import com.mannavoca.zenga.domain.block.domain.service.MemberBlockService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import com.mannavoca.zenga.domain.party.domain.service.PartyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PartyUpdateEventListener {
    private final PartyService partyService;
    private final ParticipationService participationService;
    private final BlockService blockService;
    private final MemberBlockService memberBlockService;
    private final MemberService memberService;

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void checkPartyCountAndUpdateMemberBlock(Long memberId){
        Member member = memberService.findMemberById(memberId);
        Long partyCount = participationService.getParticipationCountByMemberId(memberId);
        Long memberBlockCount = memberBlockService.getMemberBlockCountByMemberIdAndBlockType(memberId, BlockType.YELLOW);
        Long requiredPartyCount = BlockPartyMapping.getRequiredPartyCountByMemberBlockCount(memberBlockCount);
        if (requiredPartyCount != null && partyCount >= requiredPartyCount) {
            memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.YELLOW));
        }
    }
}
