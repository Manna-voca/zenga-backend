package com.mannavoca.zenga.domain.block.domain.entity.enumType;

import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;
import com.mannavoca.zenga.domain.praise.domain.entity.enumType.PraiseType;

public enum BlockType implements CodeValue {

    PINK("PINK", "외적 칭찬을 받았어요", PraiseType.APPEARANCE),
    ORANGE("ORANGE", "성격 칭찬을 받았어요", PraiseType.PERSONALITY),
    YELLOW("YELLOW", "도전﹒열정 칭찬을 받았어요", PraiseType.PASSION),
    SKY_BLUE("SKY_BLUE", "그 외 칭찬을 받았어요", PraiseType.OTHERS),
    PURPLE1("PURPLE1", "첫 모임에 참여했어요", null),
    PURPLE10("PURPLE10", "모임에 총 10번 참여했어요", null),
    PURPLE30("PURPLE30", "모임에 총 30번 참여했어요", null),
    PURPLE50("PURPLE50", "모임에 총 50번 참여했어요", null),
    LIGHT_GREEN("LIGHT_GREEN", "능력 칭찬을 받았어요", PraiseType.ABILITY),
    LIGHT_BROWN1("LIGHT_BROWN1", "첫 칭찬을 보냈어요", null),
    LIGHT_BROWN10("LIGHT_BROWN10", "칭찬을 총 10번 보냈어요", null),
    LIGHT_BROWN50("LIGHT_BROWN50", "칭찬을 총 50번 보냈어요", null),
    LIGHT_BROWN100("LIGHT_BROWN100", "칭찬을 총 100번 보냈어요", null),
    PINK_FIRST("PINK_FIRST", "첫 칭찬을 받았어요", null),
    ORANGE_FIRST("ORANGE_FIRST", "첫 칭찬을 받았어요", null),
    SKY_BLUE_FIRST("SKY_BLUE_FIRST", "첫 칭찬을 받았어요", null),
    LIGHT_GREEN_FIRST("LIGHT_GREEN_FIRST", "첫 칭찬을 받았어요", null),
    YELLOW_FIRST("PURPLE_FIRST", "첫 칭찬을 받았어요", null)

    ;

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

    public static BlockType getFirstOneFromBlockType(BlockType blockType) {
        switch (blockType) {
            case PINK:
                return PINK_FIRST;
            case ORANGE:
                return ORANGE_FIRST;
            case YELLOW:
                return YELLOW_FIRST;
            case SKY_BLUE:
                return SKY_BLUE_FIRST;
            case LIGHT_GREEN:
                return LIGHT_GREEN_FIRST;
            default:
                throw BusinessException.of(Error.INTERNAL_SERVER_ERROR);
        }
    }

    public static String getColorFromBlockType(final BlockType blockType) {
        switch (blockType) {
            case PINK:
            case PINK_FIRST:
                return "PINK";
            case ORANGE:
            case ORANGE_FIRST:
                return "ORANGE";
            case YELLOW:
            case SKY_BLUE_FIRST:
                return "SKY_BLUE";
            case SKY_BLUE:
            case LIGHT_GREEN_FIRST:
                return "LIGHT_GREEN";
            case LIGHT_GREEN:
            case YELLOW_FIRST:
                return "YELLOW";
            case PURPLE1:
            case PURPLE10:
            case PURPLE30:
            case PURPLE50:
                return "PURPLE";
            case LIGHT_BROWN1:
            case LIGHT_BROWN10:
            case LIGHT_BROWN50:
            case LIGHT_BROWN100:
                return "LIGHT_BROWN";
            default:
                return null;
        }
    }
}
