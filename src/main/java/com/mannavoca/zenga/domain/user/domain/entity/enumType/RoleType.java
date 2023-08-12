package com.mannavoca.zenga.domain.user.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;

public enum RoleType implements CodeValue {
    USER("U", "일반 사용자"),
    ADMIN("A","괸리자");

    private String code;
    private String value;

    RoleType(String code, String value) {
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
