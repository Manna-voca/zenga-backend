package com.mannavoca.zenga.domain.praise.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class TimeSectionTypeConverter extends EnumConverter<TimeSectionType> {
    TimeSectionTypeConverter(){
        super(TimeSectionType.class);
    }
}
