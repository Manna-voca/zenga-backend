package com.mannavoca.zenga.domain.user.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

public enum GenderType implements CodeValue {
    MAN("M", "남성"),
    WOMAN("W","여성");

    private String code;
    private String value;

    GenderType(String code, String value) {
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
