package com.mannavoca.zenga.domain.praise.domain.repository;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;

import java.util.List;
import java.util.Optional;

public interface MemberPraiseRepositoryCustom {
    List<MemberPraise> findAllNotPraised();

    Optional<MemberPraise> findTodayTodoPraise(Long memberId);
}
