package com.mannavoca.zenga.domain.block.domain.entity.enumType;


import com.mannavoca.zenga.common.infrastructure.domain.EnumConverter;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class BlockTypeConverter extends EnumConverter<BlockType> {
    BlockTypeConverter(){
        super(BlockType.class);
    }
}
