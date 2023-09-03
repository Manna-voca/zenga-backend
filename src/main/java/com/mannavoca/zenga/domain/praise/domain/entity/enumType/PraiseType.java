package com.mannavoca.zenga.domain.praise.domain.entity.enumType;

import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

public enum PraiseType implements CodeValue {

    APPEARANCE("AP", "외적 칭찬"),
    PERSONALITY("PE", "성격 칭찬"),
    PASSION("PA", "도전・열정 칭찬"),
    OTHERS("OT", "기타 칭찬"),
    ABILITY("AB", "능력 칭찬");

    private String code;
    private String value;

    PraiseType(String code, String value) {
        this.code = code;
        this.value = value;
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
