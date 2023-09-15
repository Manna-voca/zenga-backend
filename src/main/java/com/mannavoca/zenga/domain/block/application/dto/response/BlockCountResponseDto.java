package com.mannavoca.zenga.domain.block.application.dto.response;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class BlockCountResponseDto implements BlockCountResponseInterface{
    private String blockType;
    private Long count;

    @Builder
    public BlockCountResponseDto(String blockType, Long count) {
        this.blockType = blockType;
        this.count = count;
    }
}
