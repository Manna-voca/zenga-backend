package com.mannavoca.zenga.domain.user.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class GenderTypeConverter extends EnumConverter<GenderType> {
    GenderTypeConverter(){
        super(GenderType.class);
    }
}
