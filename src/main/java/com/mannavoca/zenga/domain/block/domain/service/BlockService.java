package com.mannavoca.zenga.domain.block.domain.service;

import com.mannavoca.zenga.common.annotation.DomainService;
import com.mannavoca.zenga.common.exception.BusinessException;
import com.mannavoca.zenga.common.exception.Error;
import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@DomainService
public class BlockService {
    private final BlockRepository blockRepository;
    public Block findBlockByBlockType(BlockType blockType) {
        return blockRepository.findByBlockType(blockType).orElseThrow(() -> BusinessException.of(Error.BLOCK_NOT_FOUND));
    }
}
