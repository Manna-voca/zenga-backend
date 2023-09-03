package com.mannavoca.zenga.domain.praise.application.service;

import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.service.BlockService;
import com.mannavoca.zenga.domain.block.domain.service.MemberBlockService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.praise.application.dto.event.PraisedMemberEventDto;
import com.mannavoca.zenga.domain.praise.domain.service.MemberPraiseService;
import com.mannavoca.zenga.domain.praise.domain.service.PraiseService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class PraiseUpdateEventListener {
    private final MemberPraiseService memberPraiseService;
    private final PraiseService praiseService;
    private final MemberBlockService memberBlockService;
    private final MemberService memberService;
    private final BlockService blockService;

    @Transactional(propagation = Propagation.REQUIRED)
    @TransactionalEventListener
    public void checkPraiseCountAndUpdateMemberBlock(final Long memberId){ // 칭찬을 한 경우
        final Long praiseCount = memberPraiseService.getFinishedMemberPraiseCountByPraiseId(memberId);
        final Member member = memberService.findMemberById(memberId);

        switch (praiseCount.intValue()){
            case 1:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.LIGHT_BROWN1));
                break;
            case 10:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.LIGHT_BROWN10));
                break;
            case 50:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.LIGHT_BROWN50));
                break;
            case 100:
                memberBlockService.createMemberBlock(member, blockService.findBlockByBlockType(BlockType.LIGHT_BROWN100));
                break;
        }

    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void updatePraisedMemberBlock(final PraisedMemberEventDto praisedMemberEventDto){
        final Member praisedMember = memberService.findMemberById(praisedMemberEventDto.getPraisedMemberId());
        if (memberPraiseService.existsReceivedPraiseByMemberId(praisedMember.getId())) {
            BlockType foundBlockType = BlockType.getFirstOneFromBlockType(BlockType.fromPraiseType(praisedMemberEventDto.getPraiseType()));
            Block foundBlock = blockService.findBlockByBlockType(foundBlockType);
            memberBlockService.createMemberBlock(praisedMember, foundBlock);
        }
        else {
            Block block = blockService.findBlockByBlockType(BlockType.fromPraiseType(praisedMemberEventDto.getPraiseType()));
            memberBlockService.createMemberBlock(praisedMember, block);
        }

    }
}
