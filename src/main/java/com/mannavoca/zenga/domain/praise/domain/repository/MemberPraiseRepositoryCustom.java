package com.mannavoca.zenga.domain.praise.domain.repository;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MemberPraiseRepositoryCustom {
    List<MemberPraise> findAllNotPraised();

    Optional<MemberPraise> findCurrentTodoPraise(Long memberId);

    Page<MemberPraise> getReceivedPraiseList(Long memberId, Pageable pageable);

    Page<MemberPraise> getMyCompletePraiseList(Long memberId, Pageable pageable);

    Long countFinishedPraiseByMemberId(Long memberId);
}
