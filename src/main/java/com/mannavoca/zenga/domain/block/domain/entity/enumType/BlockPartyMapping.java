package com.mannavoca.zenga.domain.block.domain.entity.enumType;

public enum BlockPartyMapping {
    BLOCK_COUNT_3(3L, 50L),
    BLOCK_COUNT_2(2L, 30L),
    BLOCK_COUNT_1(1L, 10L),
    BLOCK_COUNT_0(0L, 1L);
    private final Long memberBlockCount;
    private final Long requiredPartyCount;

    BlockPartyMapping(Long memberBlockCount, Long requiredPartyCount) {
        this.memberBlockCount = memberBlockCount;
        this.requiredPartyCount = requiredPartyCount;
    }

    public static Long getRequiredPartyCountByMemberBlockCount(Long memberBlockCount) {
        for (BlockPartyMapping blockPraiseMapping : BlockPartyMapping.values()) {
            if (blockPraiseMapping.memberBlockCount.equals(memberBlockCount)) {
                return blockPraiseMapping.requiredPartyCount;
            }
        }
        return null;
    }
}
