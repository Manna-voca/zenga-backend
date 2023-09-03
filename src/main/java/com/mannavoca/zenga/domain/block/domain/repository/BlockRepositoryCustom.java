package com.mannavoca.zenga.domain.block.domain.repository;

import com.mannavoca.zenga.domain.block.domain.entity.Block;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BlockRepositoryCustom {
    public List<Block> findAllByMemberId(final Long memberId);
}
