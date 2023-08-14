package com.mannavoca.zenga.domain.praise.domain.repository;

import com.mannavoca.zenga.domain.praise.domain.entity.MemberPraise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberPraiseRepository extends JpaRepository<MemberPraise, Long>, MemberPraiseRepositoryCustom{
}
