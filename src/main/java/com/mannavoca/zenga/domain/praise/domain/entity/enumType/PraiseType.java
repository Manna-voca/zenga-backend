package com.mannavoca.zenga.domain.praise.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

public enum PraiseType implements CodeValue {
    A("a", "a"),
    B("b","b");

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
