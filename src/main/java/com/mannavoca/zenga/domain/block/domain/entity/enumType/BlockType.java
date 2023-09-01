package com.mannavoca.zenga.domain.block.domain.entity.enumType;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;

public enum BlockType implements CodeValue {

    PINK("PINK", "분홍색", PraiseType.APPEARANCE),
    ORANGE("ORANGE", "주황색", PraiseType.PERSONALITY),
    SKY_BLUE("SKY_BLUE", "하늘색", PraiseType.PASSION),
    LIGHT_GREEN("LIGHT_GREEN", "연두색", PraiseType.OTHERS),
    YELLOW("YELLOW", "노란색", PraiseType.EFFORT),
    PURPLE("PURPLE", "보라색", PraiseType.EFFORT),
    LIGHT_BROWN("LIGHT_BROWN", "연갈색", PraiseType.EFFORT);

    private String code;
    private String value;
    private PraiseType category;

    BlockType(String code, String value, PraiseType category) {
        this.code = code;
        this.value = value;
        this.category = category;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }

    public PraiseType getCategory() {
        return category;
    }

    public static BlockType fromPraiseType(PraiseType praiseType) {
        for (BlockType blockType : BlockType.values()) {
            if (blockType.getCategory() == praiseType) {
                return blockType;
            }
        }
        throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
    }
}
