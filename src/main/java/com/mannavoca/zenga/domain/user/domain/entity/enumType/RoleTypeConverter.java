package com.mannavoca.zenga.domain.user.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class RoleTypeConverter extends EnumConverter<RoleType> {
    RoleTypeConverter(){
        super(RoleType.class);
    }
}
