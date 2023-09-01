package com.mannavoca.zenga.domain.block.domain.entity.enumType;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;

public enum BlockType implements CodeValue {

    PINK("PINK", "외모 칭찬", PraiseType.APPEARANCE),
    ORANGE("ORANGE", "인격 칭찬", PraiseType.PERSONALITY),
    SKY_BLUE("SKY_BLUE", "도전/열정 칭찬", PraiseType.PASSION),
    LIGHT_GREEN("LIGHT_GREEN", "기타 칭찬", PraiseType.OTHERS),
    YELLOW("YELLOW", "모임 참여", null),
    PURPLE("PURPLE", "노력 칭찬", PraiseType.EFFORT),
    LIGHT_BROWN("LIGHT_BROWN", "칭찬하기", PraiseType.EFFORT);

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
