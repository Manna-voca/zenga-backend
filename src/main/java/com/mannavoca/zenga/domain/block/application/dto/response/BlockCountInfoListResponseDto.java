package com.mannavoca.zenga.domain.block.application.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
public class BlockCountInfoListResponseDto {
    private List<BlockCountResponseInterface> blockCountResponseDtoList;
    private List<BlockInfoResponseDto> blockInfoResponseDtoList;

    @Builder
    public BlockCountInfoListResponseDto(List<BlockCountResponseInterface> blockCountResponseDtoList, List<BlockInfoResponseDto> blockInfoResponseDtoList) {
        this.blockCountResponseDtoList = blockCountResponseDtoList;
        this.blockInfoResponseDtoList = blockInfoResponseDtoList;
    }
}
