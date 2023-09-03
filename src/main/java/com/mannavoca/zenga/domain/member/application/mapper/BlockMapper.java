package com.mannavoca.zenga.domain.member.application.mapper;

import com.mannavoca.zenga.common.annotation.Mapper;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockInfoResponseDto;
import com.mannavoca.zenga.domain.block.domain.entity.Block;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class BlockMapper {
    public static BlockInfoResponseDto mapBlockToBlockInfoResponseDto(final Block block) {
        return BlockInfoResponseDto.builder()
                .id(block.getId())
                .description(block.getDescription())
                .createdAt(block.getCreatedDate().toString())
                .blockType(block.getBlockType())
                .build();
    }

    public static List<BlockInfoResponseDto> mapBlockListToBlockInfoResponseDtoList(final List<Block> blockList) {
        return blockList.stream()
                .map(BlockMapper::mapBlockToBlockInfoResponseDto)
                .collect(Collectors.toList());
    }
}
