package com.mannavoca.zenga.domain.block.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

public enum BlockType implements CodeValue {

    // 확장성을 고려해 한 글자가 아닌 색 이름으로 변경
    PINK("PINK", "분홍색", "외적 칭찬"),
    ORANGE("ORANGE","주황색", "성격 칭찬"),
    SKY_BLUE("SKY_BLUE","하늘색", "도전/열정 칭찬"),
    LIGHT_GREEN("LIGHT_GREEN","연두색", "기타 칭찬"),
    YELLOW("YELLOW","노란색", "모임 참여"),
    PURPLE("PURPLE","보라색", "노력 칭찬"),
    LIGHT_BROWN("LIGHT_BROWN","연갈색", "칭찬하기")

    ;


    private String code;
    private String value;
    private String category;

    BlockType(String code, String value, String category) {
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
}
