package com.mannavoca.zenga.domain.praise.application.service;

import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockPraiseMapping;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.service.BlockService;
import com.mannavoca.zenga.domain.block.domain.service.MemberBlockService;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import com.mannavoca.zenga.domain.member.domain.service.MemberService;
import com.mannavoca.zenga.domain.praise.application.dto.event.PraisedMemberEventDto;
import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;
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

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void checkPraiseCountAndUpdateMemberBlock(Long memberId){ // 칭찬을 한 경우
        Long praiseCount = memberPraiseService.getFinishedMemberPraiseCountByPraiseId(memberId);
        Long memberBlockCount = memberBlockService.getMemberBlockCountByMemberIdAndBlockType(memberId, BlockType.LIGHT_BROWN);
        Member member = memberService.findMemberById(memberId);
        Block block = blockService.findBlockByBlockType(BlockType.LIGHT_BROWN);

        // 멤버의 연갈색 블록 개수를 세어와서 0, 1, 2, 3에 따라 경우에 따라 나눔
        Long requiredPraiseCount = BlockPraiseMapping.getRequiredPraiseCountByMemberBlockCount(memberBlockCount);
        if (requiredPraiseCount != null && praiseCount >= requiredPraiseCount) {
            memberBlockService.createMemberBlock(member, block);
        }

    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener
    public void updatePraisedMemberBlock(PraisedMemberEventDto praisedMemberEventDto){
        Member praisedMember = memberService.findMemberById(praisedMemberEventDto.getPraisedMemberId());
        Block block = blockService.findBlockByBlockType(BlockType.fromPraiseType(praisedMemberEventDto.getPraiseType()));

        memberBlockService.createMemberBlock(praisedMember, block);
    }
}
