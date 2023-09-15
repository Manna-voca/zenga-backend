package com.mannavoca.zenga.domain.block.domain.repository;

import com.mannavoca.zenga.domain.block.application.dto.response.BlockCountResponseDto;
import com.mannavoca.zenga.domain.block.application.dto.response.BlockCountResponseInterface;
import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Long>, BlockRepositoryCustom {
    Optional<Block> findByBlockType(BlockType blockType);


    @Query(nativeQuery = true, value = "SELECT\n" +
            "  CASE\n" +
            "    WHEN b.block_type LIKE '%_FIRST' THEN SUBSTRING(b.block_type, 1, LOCATE('_FIRST', b.block_type) - 1)\n" +
            "    WHEN b.block_type REGEXP '^[a-zA-Z_]*[0-9]' THEN SUBSTRING(b.block_type, 1, LOCATE(CONVERT(REGEXP_SUBSTR(b.block_type, '[0-9]') USING utf8), b.block_type) - 1)\n" +
            "    ELSE b.block_type\n" +
            "  END AS blockType,\n" +
            "  COUNT(*) AS count\n" +
            "FROM zg_block b\n" +
            "JOIN zg_member_block mb ON b.id = mb.block_id\n" +
            "WHERE mb.member_id = :memberId\n" +
            "GROUP BY blockType;\n")
    List<BlockCountResponseInterface> findAllBlockCountListByMemberId(final Long memberId);
}
