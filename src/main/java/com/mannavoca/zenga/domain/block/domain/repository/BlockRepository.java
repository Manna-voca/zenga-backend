package com.mannavoca.zenga.domain.block.domain.repository;

import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long> {
    Optional<Block> findByBlockType(BlockType blockType);
}
