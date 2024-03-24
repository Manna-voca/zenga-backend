package com.mannavoca.zenga.domain.member.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

public enum LevelType implements CodeValue {
    NORMAL("N", "동아리원"),
    MAINTAINER("M","임원진"),
    ADMIN("A","관리자");

    private String code;
    private String value;

    LevelType(String code, String value) {
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
