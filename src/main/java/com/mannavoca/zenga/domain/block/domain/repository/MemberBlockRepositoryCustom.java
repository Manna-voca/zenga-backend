package com.mannavoca.zenga.domain.block.domain.repository;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;

public interface MemberBlockRepositoryCustom {
    Long countMemberBlockByMemberIdAndBlockType(Long memberId, BlockType blockType);
}
