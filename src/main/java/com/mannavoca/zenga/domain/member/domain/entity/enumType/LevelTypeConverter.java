package com.mannavoca.zenga.domain.member.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class LevelTypeConverter extends EnumConverter<LevelType> {
    LevelTypeConverter(){
        super(LevelType.class);
    }
}
