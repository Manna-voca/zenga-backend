package com.mannavoca.zenga.domain.block.infrastructure;

import com.mannavoca.zenga.domain.block.domain.entity.Block;
import com.mannavoca.zenga.domain.block.domain.repository.BlockRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.mannavoca.zenga.domain.block.domain.entity.QBlock.block;
import static com.mannavoca.zenga.domain.block.domain.entity.QMemberBlock.memberBlock;
@RequiredArgsConstructor
@Repository
public class BlockRepositoryImpl implements BlockRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public List<Block> findAllByMemberId(final Long memberId) {
        return queryFactory.select(block)
                .from(block)
                .join(block.memberBlockList, memberBlock)
                .fetchJoin()
                .where(memberBlock.member.id.eq(memberId))
                .fetch();
    }


}
