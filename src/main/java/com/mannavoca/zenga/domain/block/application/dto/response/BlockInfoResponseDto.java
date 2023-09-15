package com.mannavoca.zenga.domain.block.application.dto.response;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlockInfoResponseDto {
    private Long id;
    private String description;
    private String createdAt;
    private String blockType;

    @Builder
    public BlockInfoResponseDto(Long id, String description, LocalDateTime createdAt, BlockType blockType) {
        this.id = id;
        this.description = description;
        this.createdAt = DateTimeFormatter.ofPattern("yyyy.MM.dd").format(createdAt);
        this.blockType = BlockType.getColorFromBlockType(blockType);
    }
}
