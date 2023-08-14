package com.mannavoca.zenga.domain.praise.domain.repository;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;

import java.util.List;

public interface MemberPraiseRepositoryCustom {
    List<MemberPraise> findAllNotPraised();
}
