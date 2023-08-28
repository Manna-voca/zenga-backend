package com.mannavoca.zenga.domain.praise.infrastructure;

import com.mannavoca.zenga.domain.praise.domain.entity.Praise;
import com.mannavoca.zenga.domain.praise.domain.repository.PraiseRepositoryCustom;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import static com.mannavoca.zenga.domain.praise.domain.entity.QPraise.praise;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PraiseRepositoryImpl implements PraiseRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Praise findRandomPraise() {
        return queryFactory
                .selectFrom(praise)
                .orderBy(Expressions.numberTemplate(Double.class, "function('rand')").asc()) // 또는 "RAND()" (DB에 따라 다름)
                .fetchFirst();
    }
}
