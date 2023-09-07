package com.mannavoca.zenga.domain.block.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.entity.MemberBlock;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.repository.MemberBlockRepository;
import com.mannavoca.zenga.domain.block.domain.repository.MemberBlockRepositoryCustom;
import com.mannavoca.zenga.domain.member.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Slf4j
@DomainService
public class MemberBlockService {
    private final MemberBlockRepository memberBlockRepository;

    public Long getMemberBlockCountByMemberIdAndBlockType(Long memberId, BlockType blockType) {
        return memberBlockRepository.countMemberBlockByMemberIdAndBlockType(memberId, blockType);
    }

    @Transactional
    public MemberBlock createMemberBlock(Member member, Block block) {
        MemberBlock memberBlock = MemberBlock.builder()
                .block(block)
                .member(member)
                .build();

        return memberBlockRepository.save(memberBlock);
    }
}
