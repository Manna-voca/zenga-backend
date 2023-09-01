package com.mannavoca.zenga.domain.block.domain.entity.enumType;

public enum BlockPraiseMapping {
    BLOCK_COUNT_3(3L, 100L),
    BLOCK_COUNT_2(2L, 50L),
    BLOCK_COUNT_1(1L, 10L),
    BLOCK_COUNT_0(0L, 1L);
    private final Long memberBlockCount;
    private final Long requiredPraiseCount;

    BlockPraiseMapping(Long memberBlockCount, Long requiredPraiseCount) {
        this.memberBlockCount = memberBlockCount;
        this.requiredPraiseCount = requiredPraiseCount;
    }

    public static Long getRequiredPraiseCountByMemberBlockCount(Long memberBlockCount) {
        for (BlockPraiseMapping blockPraiseMapping : BlockPraiseMapping.values()) {
            if (blockPraiseMapping.memberBlockCount.equals(memberBlockCount)) {
                return blockPraiseMapping.requiredPraiseCount;
            }
        }
        return null;
    }
}
