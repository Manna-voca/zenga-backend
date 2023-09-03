package com.mannavoca.zenga.domain.block.application.dto.response;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import lombok.*;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BlockInfoResponseDto {
    private Long id;
    private String description;
    private String createdAt;
    private BlockType blockType;
}
