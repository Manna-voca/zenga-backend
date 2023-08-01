package com.mannavoca.zenga.domain.badge.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class BadgeTypeConverter extends EnumConverter<BadgeType> {
    BadgeTypeConverter(){
        super(BadgeType.class);
    }
}
