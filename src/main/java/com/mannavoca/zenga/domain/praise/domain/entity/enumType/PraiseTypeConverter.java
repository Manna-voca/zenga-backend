package com.mannavoca.zenga.domain.praise.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class PraiseTypeConverter extends EnumConverter<PraiseType> {
    PraiseTypeConverter(){
        super(PraiseType.class);
    }
}
