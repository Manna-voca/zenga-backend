package com.mannavoca.zenga.domain.member.domain.entity.enumType;

import com.mannavoca.zenga.common.infrastructure.domain.CodeValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public enum State implements CodeValue {
    RECRUITING("R", "모집중"),
    IN_PROGRESS("I", "진행중"),
    COMPLETED("C", "완료");

    private final String code;
    private final String value;

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
