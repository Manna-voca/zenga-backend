package com.mannavoca.zenga.domain.party.application.service;

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
    private final ParticipationService participationService;
    private final BlockService blockService;
    private final MemberBlockService memberBlockService;
    private final MemberService memberService;

    @Transactional(propagation = Propagation.REQUIRED)
    @TransactionalEventListener
    public void checkPartyCountAndUpdateMemberBlock(Long memberId){
        Member member = memberService.findMemberById(memberId);
        Long partyCount = participationService.getParticipationCountByMemberId(memberId);

        switch (partyCount.intValue()){
            case 1:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.YELLOW1));
                break;
            case 10:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.YELLOW10));
                break;
            case 30:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.YELLOW30));
                break;
            case 50:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.YELLOW50));
                break;
        }
    }
}
