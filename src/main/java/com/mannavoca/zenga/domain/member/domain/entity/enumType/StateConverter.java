package com.mannavoca.zenga.domain.member.domain.entity.enumType;

import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class StateConverter extends EnumConverter<State> {
    StateConverter(){
        super(State.class);
    }
}
