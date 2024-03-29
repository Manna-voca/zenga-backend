package com.mannavoca.zenga.domain.party.application.service;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.service.BlockService;
import com.mannavoca.zenga.domain.block.domain.service.MemberBlockService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.party.domain.service.ParticipationService;
import lombok.RequiredArgsConstructor;
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
        Long partyCount = participationService.getFinishedParticipationCountByMemberId(memberId);

        switch (partyCount.intValue()){
            case 1:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.PURPLE1));
                break;
            case 10:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.PURPLE10));
                break;
            case 30:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.PURPLE30));
                break;
            case 50:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.PURPLE50));
                break;
        }
    }
}
