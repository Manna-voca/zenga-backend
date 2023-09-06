package com.mannavoca.zenga.domain.block.infrastructure;

import com.mannavoca.zenga.domain.block.domain.entity.enumType.BlockType;
import com.mannavoca.zenga.domain.block.domain.repository.MemberBlockRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.mannavoca.zenga.domain.block.domain.entity.QMemberBlock.memberBlock;
import static com.mannavoca.zenga.domain.block.domain.entity.QBlock.block;

@RequiredArgsConstructor
@Repository
public class MemberBlockRepositoryImpl implements MemberBlockRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Long countMemberBlockByMemberIdAndBlockType(Long memberId, BlockType blockType) {
        return queryFactory.select(memberBlock.count())
                .from(memberBlock)
                .innerJoin(memberBlock.block, block)
                .fetchJoin()
                .where(
                        memberBlock.member.id.eq(memberId).and(block.blockType.eq(blockType))
                )
                .fetchOne();

    }
}
